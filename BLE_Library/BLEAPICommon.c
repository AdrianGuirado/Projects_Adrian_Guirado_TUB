/*
 * BLEAPICommon.c
 *
 *  Created on: 20 sept. 2023
 *      Author: AdriUOC
 *
 */

// C File - BLEAPICommon.c

#include "BLEAPICommon.h"
#include "main.h"
#include "enumtype.h"
#include "string.h"
#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>

#define OK_LENGTH 4
#define CHECK_LENGTH 2
#define SCANEND_LENGTH 4
#define CONNECT_LENGTH 8

UART_HandleTypeDef huart1;
ConnectionInf test;

// Function to send data via UART

device_scanned_characteristics scanned_device;

uint8_t UART_SendData(const uint8_t *data, uint32_t size) {
	uint8_t result = 0;
    result = HAL_UART_Transmit(&huart1, data, size, HAL_MAX_DELAY);
    return result;
}

status_t UART_ReceiveData(uint8_t *buffer, uint32_t size, char* terminationChar) {
    status_t result = STATUS_ERROR;
    uint32_t i = 0;
    uint8_t x = ' ';
    int aux = -1;

    if (strcmp(terminationChar, TERMINATION_AT) == 0) {
        aux = 0;
    }
    else if (strcmp(terminationChar, TERMINATION_OK) == 0) {
        aux = 1;
    }
    else if (strcmp(terminationChar, TERMINATION_CHECK) == 0) {
        aux = 2;
    }
    else if (strcmp(terminationChar, TERMINATION_SCAN) == 0) { // Fixed the repeated condition
        aux = 3;
    }
    else if(strcmp(terminationChar, TERMINATION_CONNECT) == 0) {
    	aux = 4;
    }
    else if(strcmp(terminationChar, TERMINATION_READ) == 0){
    	aux = 5;
    }

    while (i < size) {
        if( HAL_UART_Receive(&huart1, &x, 1, 1000) != HAL_OK) {
            return STATUS_ERROR; // Directly return error status if receive failed
        }

        buffer[i++] = x;

        switch(aux) {
            case 0: // AT termination
                if(x == '\r') return STATUS_OK;
                break;
            case 1: // OK termination
                if(i >= OK_LENGTH && buffer[i-4] == '\n' && buffer[i-3] == 'O' && buffer[i-2] == 'K' && buffer[i-1] == '\r') return STATUS_OK;
                break;
            case 2: // CHECK termination
                if(i >= CHECK_LENGTH && buffer[i-1] == '\r' && buffer[i-2] == '0') return STATUS_OK;
                else if(i >= CHECK_LENGTH) return STATUS_OK;
                break;
            case 3: // OTHER termination, assumed for 'end'
                if(i >= SCANEND_LENGTH && buffer[i-4] == 'e' && buffer[i-3] == 'n' && buffer[i-2] == 'd' && buffer[i-1] == '\r') return STATUS_OK;

                break;
            case 4:
            	if( i >= CONNECT_LENGTH && buffer[0] == '\n'
            							&& buffer[1] == 'c'
            							&& buffer[2] == 'o'
            							&& buffer[3] == 'n'
            							&& buffer[4] == 'n'
            							&& buffer[5] == 'e'
            							&& buffer[6] == 'c'
            							&& buffer[7] == 't'
            							&& buffer[i-1] == '\r') return STATUS_CONNECTED;
            		break;
            case 5:
            	uint8_t counter = 0;
            	if(buffer[i] == '\r'){
            		counter++;
            		if (counter == 2){
            			return STATUS_OK;
            		}
            	}
            	break;
            default:
                // Unhandled aux value, you may want to handle this case as well.
                break;
        }
    }

    result = ErrorHandler(buffer);

    return result;
}

status_t ErrorHandler(uint8_t *buffer){

	int number;

	if (sscanf((char*)buffer, "\nERROR_%d\r", &number) == 1){
		switch (number){
		        case 1: return STATUS_ERROR_01 ;
		        case 2: return STATUS_ERROR_02;
		        case 5: return STATUS_ERROR_05;
		        case 9: return STATUS_ERROR_09;
		        case 14: return STATUS_ERROR_14;
		        case 15: return STATUS_ERROR_15;
		        case 33: return STATUS_ERROR_33;
		        case 46: return STATUS_ERROR_46;
		        case 47: return STATUS_ERROR_47;
		        case 48: return STATUS_ERROR_48;
		        case 49: return STATUS_ERROR_49;
		        case 50: return STATUS_ERROR_50;
		        case 51: return STATUS_ERROR_51;
		        case 52: return STATUS_ERROR_52;
		        case 53: return STATUS_ERROR_53;
		        case 54: return STATUS_ERROR_54;
		        case 55: return STATUS_ERROR_55;
		        case 56: return STATUS_ERROR_56;
		        case 57: return STATUS_ERROR_57;
		        case 58: return STATUS_ERROR_58;
		        case 59: return STATUS_ERROR_59;
		        case 62: return STATUS_ERROR_62;
		        case 63: return STATUS_ERROR_63;
		        case 64: return STATUS_ERROR_64;
		        case 65: return STATUS_ERROR_65;
		        case 66: return STATUS_ERROR_66;
		        case 67: return STATUS_ERROR_67;
		        case 68: return STATUS_ERROR_68;
		        case 71: return STATUS_ERROR_71;
		        case 74: return STATUS_ERROR_74;
		        case 75: return STATUS_ERROR_75;
		        case 77: return STATUS_ERROR_77;
		        case 79: return STATUS_ERROR_79;
		        case 82: return STATUS_ERROR_82;
		        case 99: return STATUS_ERROR_99;
		        default: return STATUS_ERROR_XX;
		}
	}
	else{
		return STATUS_OK;
	}
}


void decimalToHexadecimal(const char* decimal, char* hexadecimal) {
    long int number = strtol(decimal, NULL, 10);
    sprintf(hexadecimal, "%lX", number);

}

void BLEAPI_Parse_Connection_Response(uint8_t *response, size_t responseSize, ConnectionInf *connection)
{
    if (response == NULL || connection == NULL){
        return;
    }

    char *token = strtok((char *)response, " ,\n\r");

    connection->Vsp = isupper(token[0]) ? true : false;

    token = strtok(NULL, " ,");
    if (token != NULL){
        connection->hIdx = (uint8_t)atoi(token);
    }

    token = strtok(NULL, " ,");

    if (token != NULL){
        strncpy(connection->address, token, MAX_ADDRESS_LENGTH);
        connection->address[MAX_ADDRESS_LENGTH] = '\0';
    }
}

void BLE_Remove_Connection(ble_device_info_t *deviceInfo, uint8_t hIdx) {
    if (deviceInfo == NULL) {
        return;
    }

    for (int i = 0; i < MAX_CONNECTIONS; i++) {
        if (deviceInfo->connections[i].hIdx == hIdx) {
            deviceInfo->connections[i].hIdx = 0;
            deviceInfo->connections[i].address[0] = '\0';
            deviceInfo->connections[i].Vsp = false;
            deviceInfo->connections_count--;
            break;
        }
    }
}
uint8_t BLEAPI_SendATCommand(const char* command) {

    char new_command[COMMAND_BUFFER_SIZE];
    uint8_t result = 0;
    sprintf(new_command, "%s\r", (const char*)command);
    result = UART_SendData((uint8_t*)new_command, strlen(new_command));
    if(result != HAL_OK){
    	return result;
    }
    return STATUS_OK;
}

uint8_t BLEAPI_CheckATResponse(uint8_t* response_buffer) {
    char* target_string = "\nOK\r";
    char* found_position = strstr((char*)response_buffer, target_string);

    if (found_position != NULL) {
        return STATUS_OK;
    } else {
        return STATUS_ERROR;
    }
}

bool BLEAPI_DeviceExists(ble_scan_results *results, char *address) {
    for (int i = 0; i < results->count; i++) {
        if (strcmp(results->devices[i].address, address) == 0) {
            return true;
        }
    }
    return false;
}

void BLEAPI_ParseScanResults(uint8_t *buffer, uint32_t size, ble_scan_results *test){

	char *new_line = strtok((char*)buffer, "\n");

	test->count = 0;

	while (new_line != NULL){
	    device_scanned_characteristics scanned_device = {0, "", ""};

	    sscanf(new_line, "%*s %14s %d", scanned_device.address, &scanned_device.RSSI);
	    new_line = strtok(NULL, "\n");

	    if (BLEAPI_DeviceExists(test, scanned_device.address)) {
	        continue;
	    }
	    if (test->count >= 128) {
	        break;
	    }

	    test->devices[test->count] = scanned_device;
	    test->count++;
	}
}

void BLEAPI_ParseGCTM(const uint8_t *response, uint32_t size, UUIDArray *array){

    char *line = strtok((char*)response, "\r\n");

    while (line != NULL) {
        char *colon1 = strchr(line, ':');
        if (colon1 != NULL) {
            array->count++;
            char *colon2 = strchr(colon1 + 1, ':');
            if (colon2 != NULL) {
                colon2++;
                uint8_t number;
                if (sscanf(colon2, "%d", &number) == 1) {
                    array->uuids[array->count].number = number;
                }
                char *feStart = strstr(colon2, "FE");
                if (feStart != NULL) {
                	char feBuffer[8];
                	sscanf(feStart, "%s", feBuffer);
                    strncpy(array->uuids[array->count].uuid, feBuffer, 8);
                }
            }
        }
        line = strtok(NULL, "\r\n");
    }
}


void BLEAPI_ParseRead(uint8_t *response, uint32_t size, uint8_t *value)
{
    char* lastSpace = strrchr((char*)response, ' ');

    if (lastSpace != NULL) {
        int lastIndex = lastSpace - (char*)response;
        uint8_t j = 0;
        for(uint8_t i = lastIndex + 1; i < size - 1 && response[i] != '\0' && j < size - lastIndex - 2; i++) {
            value[j++] = response[i];
        }
        value[j] = '\0';
    } else {
        value[0] = '\0';
    }
}

void BLE_RemoveConnection(ble_device_info_t *deviceInfo, uint8_t hIdx) {
    if (deviceInfo == NULL) {
        return;
    }

    for (int i = 0; i < MAX_CONNECTIONS; i++) {
        if (deviceInfo->connections[i].hIdx == hIdx) {
            deviceInfo->connections[i].hIdx = 0;
            deviceInfo->connections[i].address[0] = '\0';
            deviceInfo->connections[i].Vsp = false;
            deviceInfo->connections_count--;
            break;
        }
    }
}

status_t BLEAPI_ParseAddress(uint8_t *response, uint32_t size, char *outBuffer, uint32_t outBufferSize){
    if (outBufferSize < 15) {
        return STATUS_ERROR;
    }

    memset(outBuffer, 0, outBufferSize);

    if (size >= MAX_DEVICE_ADDRESS_LENGTH) {
        int i = 0;
        while (i < size && !isxdigit(response[i])) {
            i++;
        }
        if (i + MAX_DEVICE_ADDRESS_LENGTH <= size) {
            memcpy(outBuffer, response + i, MAX_DEVICE_ADDRESS_LENGTH);
            outBuffer[MAX_DEVICE_ADDRESS_LENGTH] = '\0';
        }
    }
    return STATUS_OK;
}

int find_number_by_uuid(const UUIDArray* array, const char* uuid) {

    for (uint8_t i = 0; i < array->count; i++) {
        if (strncmp(array->uuids[i].uuid, uuid, MAX_DEVICE_ADDRESS_LENGTH) == 0) {
            return array->uuids[i].number;
        }
    }
    return -1;
}

status_t BLE_ValidPasskey(uint32_t passkey) {

    char buffer[12];
    sprintf(buffer, "%06u", passkey);

    if (strlen(buffer) == 6) {
        return 1;
    } else {
        return 0;
    }
}

status_t CC_Parser(uint8_t *response, uint32_t size, ble_device_info_t *device){

	int id, state;

	bool auxnotify = false;
	bool auxindicate = false;

	if(sscanf((char*)response, "\nCC:%d, %d\r", &id, &state) == 2)
	{
		if (state == 1)
		{
			auxnotify = true;
			auxindicate = false;

		}
		else if(state == 2)
		{
			auxnotify = false;
			auxindicate = true;
		}
		else
		{
			auxnotify = false;
			auxindicate = false;
		}
		for (int i = 0; i < device->services_count; i++)
		{
			for (int j = 0; j < device->services[i].characteristics_count; j++)
			{
				if (device->services[i].characteristics[j].id_gscb == id)
				{
					device->services[i].characteristics[j].notify = auxnotify;
					device->services[i].characteristics[j].indicate = auxindicate;
					return STATUS_OK;
				}
			}
		}
	}

	return STATUS_ERROR;
}

status_t WR_Parser(uint8_t *response, uint32_t size, ble_device_info_t *device){

	uint8_t id;
	char value[MAX_SIZE_VALUE];

	if(sscanf((char*)response, "\nWR:%d, %s\r", &id, &value) == 2){
		for (int i = 0; i < device->services_count; i++)
		{
			for (int j = 0; j < device->services[i].characteristics_count; j++)
			{
				if (device->services[i].characteristics[j].id_gscb == id)
				{
					strcpy((char*)device->services[i].characteristics[j].value, value);
					return STATUS_OK;
				}
			}
		}
	}
    return STATUS_OK;
}
status_t IN_Parser(uint8_t *response, uint32_t size, uint8_t *buffer_reader) {
    if (response == NULL || buffer_reader == NULL) {
        return STATUS_ERROR;
    }

    uint8_t id;
    uint8_t id_ccdd;
    char value[90];

    if (sscanf((char*)response, "\nIN:%d, %d, %49s\r", &id, &id_ccdd, value) == 3) { // Format id_connection;id_ccdd;value
        sprintf((char*)buffer_reader, "%d;%d;%s", id, id_ccdd, value);
        return STATUS_NOTIFICATION;
    } else {
        return STATUS_ERROR;
    }

    return STATUS_OK;
}

HeaderType getHeaderType(char *header) {
	if (strcmp(header, "\nTM") == 0) return HEADER_TM;
	if (strcmp(header, "\nADAD") == 0) return HEADER_ADAD;
	if (strcmp(header, "\nTXPO") == 0) return HEADER_TXPO;
	if (strcmp(header, "\nCSEC") == 0) return HEADER_CSEC;
	if (strcmp(header, "\nAB") == 0) return HEADER_AB;
	if (strcmp(header, "\nAD") == 0) return HEADER_AD;
	if (strcmp(header, "\nAE") == 0) return HEADER_AE;
	if (strcmp(header, "\nAK") == 0) return HEADER_AK;
	if (strcmp(header, "\nAR") == 0) return HEADER_AR;
	if (strcmp(header, "\nAS") == 0) return HEADER_AS;
	if (strcmp(header, "\nAW") == 0) return HEADER_AW;
	if (strcmp(header, "\nCC") == 0) return HEADER_CC;
	if (strcmp(header, "\nFC") == 0) return HEADER_FC;
	if (strcmp(header, "\nFD") == 0) return HEADER_FD;
	if (strcmp(header, "\nIN") == 0) return HEADER_IN;
	if (strcmp(header, "\nSC") == 0) return HEADER_SC;
	if (strcmp(header, "\nWR") == 0) return HEADER_WR;
	if (strcmp(header, "\nLL") == 0) return HEADER_LL;
	if (strcmp(header, "\nPI") == 0) return HEADER_PI;
	if (strcmp(header, "\nSR") == 0) return HEADER_SR;
	if (strcmp(header, "\nCP") == 0) return HEADER_CP;
	if (strcmp(header, "\nMT") == 0) return HEADER_MT;
	if (strcmp(header, "\nPU") == 0) return HEADER_PU;
	if (strcmp(header, "\nPF") == 0) return HEADER_PF;
	if (strcmp(header, "\nCONNECT") == 0) return HEADER_CONNECT;
	if (strcmp(header, "\nERROR") == 0) return HEADER_ERROR;
	if (strcmp(header, "\nNOCARRIER") == 0) return HEADER_NOCARRIER;
	if (strcmp(header, "\nRING") == 0) return HEADER_RING;
	if (strcmp(header, "\nshowcode") == 0) return HEADER_SHOWCODE;
	if (strcmp(header, "\ncomparecode") == 0) return HEADER_COMPARECODE;
	if (strcmp(header, "\nscanned") == 0) return HEADER_SCANNED;
	if (strcmp(header, "\ndiscon") == 0) return HEADER_DISCON;
	if (strcmp(header, "\nencrypt") == 0) return HEADER_ENCRYPT;
	if (strcmp(header, "\npasskey?") == 0) return HEADER_PASSKEY;
	if (strcmp(header, "\noobkey?") == 0) return HEADER_OOBKEY;
	if (strcmp(header, "\nlescoob?") == 0) return HEADER_LESCOOB;
	if (strcmp(header, "\nxxkey?") == 0) return HEADER_XXKEY;
	return HEADER_UNKNOWN;
}

status_t Parser(uint8_t *response, uint32_t size, ble_device_info_t *device, uint8_t *buffer_reader) {

    status_t result = 0;

    char *copy = (char *)malloc(size + 1);
    if (copy == NULL) {
        return STATUS_MEMORY_ERROR;
    }
    memcpy(copy, response, size);
    copy[size] = '\0';

    char *header;
    char *delimiter;

    if (strchr(copy, ':') != NULL) {
        delimiter = ":";
    } else {
        delimiter = " ";
    }

    header = strtok(copy, delimiter);

    if (header) {
        HeaderType type = getHeaderType(header);
        switch (type) {
            case HEADER_TM:
                break;
            case HEADER_ADAD:
                break;
            case HEADER_TXPO:
                break;
            case HEADER_CSEC:
                break;
            case HEADER_AB:
                break;
            case HEADER_AD:
                break;
            case HEADER_AE:
                break;
            case HEADER_AK:
                break;
            case HEADER_AR:
                break;
            case HEADER_AS:
                break;
            case HEADER_AW:
                break;
            case HEADER_CC:
            	CC_Parser(response, size, device);
                break;
            case HEADER_FC:
                break;
            case HEADER_FD:
                break;
            case HEADER_IN:
            	IN_Parser(response, size, buffer_reader);
                break;
            case HEADER_SC:
                break;
            case HEADER_WR:
            	WR_Parser(response, size, device);
                break;
            case HEADER_LL:
                break;
            case HEADER_PI:
                break;
            case HEADER_SR:
                break;
            case HEADER_CP:
                break;
            case HEADER_MT:
                break;
            case HEADER_PU:
                break;
            case HEADER_PF:
                break;
            case HEADER_CONNECT:
                break;
            case HEADER_ERROR:
                break;
            case HEADER_NOCARRIER:
                break;
            case HEADER_RING:
                break;
            case HEADER_SHOWCODE:
                break;
            case HEADER_COMPARECODE:
                break;
            case HEADER_SCANNED:
                break;
            case HEADER_DISCON:
            	result = STATUS_DISCONNECTED;
                break;
            case HEADER_ENCRYPT:
                break;
            case HEADER_PASSKEY:
                break;
            case HEADER_OOBKEY:
                break;
            case HEADER_LESCOOB:
                break;
            case HEADER_XXKEY:
                break;
            case HEADER_UNKNOWN:
            default:
                break;
        }
    }
    free(copy);
    return result;
}


char* stringToHex(const char* input) {
    if (input == NULL) {
        return NULL;
    }
    size_t len = strlen(input);
    char* hexOutput = (char*)malloc((len * 2 + 1) * sizeof(char));

    if (hexOutput == NULL) {
        return NULL;
    }

    for (size_t i = 0; i < len; i++) {
        sprintf(hexOutput + i * 2, "%02X", (unsigned char)input[i]);
    }

    hexOutput[len * 2] = '\0';
    return hexOutput;
}

void byte_to_hex(unsigned char byte, char *hex_buffer) {
    const char hex_digits[] = "0123456789ABCDEF";
    hex_buffer[0] = hex_digits[(byte >> 4) & 0x0F];
    hex_buffer[1] = hex_digits[byte & 0x0F];
    hex_buffer[2] = '\0';
}

status_t notify_buffer(const char *buffer_reader, uint8_t *id, uint8_t *id_ccdd, char *value) {
    if (buffer_reader == NULL || id == NULL || id_ccdd == NULL || value == NULL) {
        return STATUS_ERROR;
    }

    if (sscanf(buffer_reader, "%d;%d;%49s", id, id_ccdd, value) == 3) {
        return STATUS_OK;
    } else {
        return STATUS_ERROR;
    }
}

void concatenate_notify(uint8_t dest[], uint8_t dest_size, uint32_t *current_size, uint8_t  source[], uint8_t source_size) {
    if (*current_size + source_size > dest_size) {
        return;
    }
    memcpy(dest + *current_size, source, source_size * sizeof(int));
    *current_size += source_size;
}



