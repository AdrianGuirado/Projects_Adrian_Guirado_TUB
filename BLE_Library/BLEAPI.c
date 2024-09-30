// C File - BLEAPI.c
#include "BLEAPI.h"
#include "BLEAPICommon.h" // Include the BLE API Common commands and UART management functions
#include "ConfigParameters.h"
#include "stdlib.h"
#include "string.h"
#include "stdio.h"

#define COMMAND_BUFFER_SIZE 50
#define DATA_BUFFER_SIZE 256


//gloabal variables

ble_scan_results scan_results;
UUIDArray array = {0};

void ble_initialize_gatt_server(ble_device_info_t *device_info) {
    device_info->advertisment = false;
    device_info->is_connected = false;
    device_info->services_count = 0;
    device_info->connections_count = 0;

    memset(device_info->services, 0, sizeof(device_info->services));
    memset(device_info->connections, 0, sizeof(device_info->connections));
}

status_t sendATCommandAndCheckResponse(const char *ATCommand, uint8_t *response, uint32_t size, char *expectedTermination) {

	status_t auxsend, auxreceive;

    if ((auxsend = BLEAPI_SendATCommand(ATCommand)) != STATUS_OK) {
        return STATUS_SEND_ERROR;
    }

    if ((auxreceive = UART_ReceiveData(response, size, expectedTermination)) != STATUS_OK) {
        return STATUS_RECEIVE_ERROR;
    }

    return STATUS_OK;
}


status_t ble_reset()
{
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
    status_t result;

    snprintf((char*)new_command, sizeof(new_command), "%s", ATZ);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response), TERMINATION_OK);

    return result;
}

status_t ble_default_parameters()
{
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
    status_t result;

    snprintf((char*)new_command, sizeof(new_command), "%s", AT_F);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

    return result;
}


status_t ble_io_capability_parameter(IO_Parameter value)
{
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
	status_t result;

	snprintf((char*)new_command, sizeof(new_command), "%s %d", ATS_107, value);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

    return result;
}


status_t ble_init(ble_device_info_t *device) {
	uint8_t new_command[SIZE_BUFFER_COMMAND];
    uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
    device->services_count = 0;
	status_t result;

    snprintf((char*)new_command, sizeof(new_command), "%s 100=%d", ATS, VSPmode);
    
	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

    snprintf((char*)new_command, sizeof(new_command), "%s 101=%d", ATS, TxPower);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

	result = sendATCommandAndCheckResponse((const char*)AT_W, response,sizeof(response),TERMINATION_OK);
    
	if (result!=STATUS_OK){
		return result;
	}

	result = sendATCommandAndCheckResponse((const char*)ATZ, response,sizeof(response),TERMINATION_OK);
    
	return result;
}

status_t ble_listening(ConnectionInf connection, ble_device_info_t *device)
{
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
	status_t result = 0;

	ble_start_advertisment(device);

	while(result != STATUS_CONNECTED)
	{
		result = UART_ReceiveData(response, sizeof(response), TERMINATION_CONNECT)	;
	}
	device->is_connected = true;

	BLEAPI_Parse_Connection_Response(response, sizeof(response), &connection);
	device->connections[0] = connection;

	device->connections_count++;
	return result;
}


status_t ble_handle_advertisment(const char* command, bool advertismentState, ble_device_info_t *device) {
    uint8_t response[SIZE_BUFFER_RESPONSE_SMALL] = "";
    status_t result;

	result = sendATCommandAndCheckResponse((const char*)command, response,sizeof(response),TERMINATION_OK);

    if(result == STATUS_OK) {
        device->advertisment = advertismentState;
    }
    return result;
}

status_t ble_start_advertisment(ble_device_info_t *device) {
    return ble_handle_advertisment(AT_LADV, true, device);
}

status_t ble_stop_advertisment(ble_device_info_t *device) {
    return ble_handle_advertisment(AT_LADVX, false, device);
}

status_t ble_start_scan(ble_scan_results *results) {
	uint8_t response[20000] = {0};
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	status_t result;

	snprintf((char*)new_command, sizeof(new_command), "%s", AT_LSCN);
	result = sendATCommandAndCheckResponse((const char*)new_command, response, sizeof(response), TERMINATION_SCAN);

	if (result!=STATUS_OK){
		return result;
	}

    BLEAPI_ParseScanResults(response, sizeof(response), results);

    return result;
}

status_t ble_gssb(ble_service_info_t *service){

	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
	status_t result;

	snprintf((char*)new_command, sizeof(new_command), "%s %d", AT_GSSB, service->index);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response), TERMINATION_OK);

	return result;
}

status_t ble_create_service(ble_service_info_t *service, ble_device_info_t *device){
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];

	status_t result;

	if (service->index > MAX_SERVICES){
		return STATUS_ERROR;
	}

    memset(response, 0, sizeof(response));

    snprintf((char*)new_command, sizeof(new_command), "%s %d,%s", AT_UUID, service->index, service->uuid);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response), TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

	snprintf((char*)new_command, sizeof(new_command), "%s %d", AT_GSSB, service->index);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response), TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

	if(service->len_Data > MAX_DATA_SERVICE){
		return STATUS_ERROR;
	}

	snprintf((char*)new_command, sizeof(new_command), "%s %d, %d, %d, %d", AT_GSMD, service->index, service->rdRights, service->wrRights, service->len_Data);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response), TERMINATION_OK);

	return STATUS_OK;
}

status_t ble_end_service(ble_service_info_t *service, ble_device_info_t *device){
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL] = {0};
	status_t result;

	result = sendATCommandAndCheckResponse(AT_GSSE, response,sizeof(response), TERMINATION_OK);

	service->open = false;
	if (device->services_count < MAX_SERVICES){
		    device->services[device->services_count] = *service;
		    device->services_count++;
	}

    return STATUS_OK;
}


status_t ble_start_characteristic(ble_characteristic_params_t *characteristic, ble_service_info_t *service)
{
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL] = {0};
	status_t result;

	if(service->open == false){
		return STATUS_ERROR;
	}

	snprintf((char*)new_command, sizeof(new_command), "%s %d,%s", AT_UUID, characteristic->uuidindex, characteristic->uuid);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response), TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

	if(characteristic->cccdIndex > 0 && characteristic->sccdIndex > 0){
		snprintf((char*)new_command, sizeof(new_command), "%s %d, %d, %d, %d, %d", AT_GSCB, characteristic->uuidindex,
		         characteristic->properties, characteristic->metadataIndex, characteristic->cccdIndex,
		         characteristic->sccdIndex);
	}
	else if(characteristic->cccdIndex > 0 && characteristic->sccdIndex < 0){
		snprintf((char*)new_command, sizeof(new_command), "%s %d, %d, %d, %d", AT_GSCB, characteristic->uuidindex,
		         characteristic->properties, 1 /* characteristic->metadataIndex */, characteristic->cccdIndex);
	}
	else if(characteristic->cccdIndex < 0 && characteristic->sccdIndex > 0){
		snprintf((char*)new_command, sizeof(new_command), "%s %d, %d, %d,, %d", AT_GSCB, characteristic->uuidindex,
		         characteristic->properties, 1 /* characteristic->metadataIndex */, characteristic->sccdIndex);
	}
	else{
		snprintf((char*)new_command, sizeof(new_command), "%s %d, %d, %d", AT_GSCB, characteristic->uuidindex,
		         characteristic->properties, 1 /* characteristic->metadataIndex */);
	}

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response), TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

	snprintf((char*)new_command, sizeof(new_command), "%s %s", AT_GSCE, characteristic->initial_value);
    memset(response, 0, sizeof(response));

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response), TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

	characteristic->id_gscb = atoi(&response[1]);

	service->characteristics[service->characteristics_count] = *characteristic;
	service->characteristics_count++;

	return STATUS_OK;

}

status_t ble_change_value_characteristic(ble_characteristic_params_t *characteristic, char* value, prop_characteristic type){
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE] = {0};
	status_t result;

	//Some code prepared when changing for freeRTOS
	//if((characteristic->properties & NOTIFIABLE_CH) && (type == NOTIFIABLE_CH) && (characteristic->notify == true))

	if(type == NOTIFIABLE_CH)
	{
		snprintf((char*)new_command, sizeof(new_command), "%s %d, %s", AT_GSNO, characteristic->id_gscb, value);
	}
	//if((characteristic->properties & READABLE_BY_CLIENT_CH) && (type = READABLE_BY_CLIENT_CH))
	if(type == READABLE_BY_CLIENT_CH)
	{
		snprintf((char*)new_command,sizeof(new_command), "%s %d, %s", AT_GSWC, characteristic->id_gscb, value);
	}
	//if((characteristic-> properties & INDICATABLE_CH) && (type = INDICATABLE_CH) && (characteristic->indicate == true))
	if(type == INDICATABLE_CH)
	{
		snprintf((char*)new_command,sizeof(new_command), "%s %d, %s", AT_GSIC, characteristic->id_gscb, value);
	}

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response), TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

	return result;
}

status_t ble_save(){

    uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
    status_t result;
    uint8_t new_command[SIZE_BUFFER_COMMAND];

    snprintf((char*)new_command,sizeof(new_command), "%s", AT_W);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

	snprintf((char*)new_command,sizeof(new_command), "%s", ATZ);
	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

    return result;
}

int extract_number_after_connect(const uint8_t *response, size_t size) {
    const char *keyword = "connect";
    char *ptr = NULL;
    char response_copy[SIZE_BUFFER_RESPONSE_SMALL + 1];

    if (size > SIZE_BUFFER_RESPONSE_SMALL) {
        size = SIZE_BUFFER_RESPONSE_SMALL;
    }

    strncpy(response_copy, (const char *)response, size);
    response_copy[size] = '\0';

    ptr = strstr(response_copy, keyword);
    if (ptr) {
        ptr += strlen(keyword);
        while (*ptr && (*ptr < '0' || *ptr > '9')) {
            ptr++;
        }
        if (*ptr) {
            return atoi(ptr);
        }
    }

    return -1;
}
status_t ble_connect(uint8_t *hIdx){
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL] = {0};
	status_t result;


	memset(response, 0, sizeof(response));

	snprintf((char*)new_command, sizeof(new_command),"%s %s", AT_LCON, MAC_CONNECT);

	result = sendATCommandAndCheckResponse((const char*)new_command, response, sizeof(response), TERMINATION_AT);

    int number = extract_number_after_connect(response, sizeof(response));
    if (number != -1) {
        *hIdx = (uint8_t)number;
    } else {
        *hIdx = 0; // Valor por defecto si no se encuentra el número
    }

    return result;
}

status_t ble_gctm(){

	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE] = {0};
	status_t result;

    memset(response, 0, sizeof(response));
    memset(new_command, 0, sizeof(new_command));

	sprintf((char*)new_command, sizeof(new_command),"%s %d", AT_GCTM, INDEX_CONNECTION);

	result = sendATCommandAndCheckResponse((const char*)new_command, response, sizeof(response), TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

	BLEAPI_ParseGCTM(response, sizeof(response), &array);

	return result;
}

status_t ble_client_read_characteristic(const char* uuid, uint8_t *value, uint32_t valueSize, uint16_t Offset){

	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL] = {0};
	status_t result;
	uint8_t aux = 0;
	int uuid_len = strlen(uuid);
		if (uuid_len < 4) {
			return STATUS_ERROR;
		}

	for (uint8_t i = 0; i < array.count; i++) {
		        if (strncmp(array.uuids[i].uuid, uuid, 8) == 0) {
		            aux = array.uuids[i].number;
		        }
		    }

	memset(response, 0, sizeof(response));

    snprintf((char*)new_command, sizeof(new_command),"%s %d, %d, %d", AT_GCRD, INDEX_CONNECTION, 30, Offset);

	result = sendATCommandAndCheckResponse((const char*)new_command, response, sizeof(response), TERMINATION_READ);

	/*if (result!=STATUS_OK){
		return result;
	}*/

	BLEAPI_ParseRead(response, sizeof(response), value);

    return result;
}

status_t ble_client_write_characteristic(const char* uuid, char* string){

	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE] = {0};
	uint8_t aux = 0;
	status_t result;

    for (uint8_t i = 0; i < array.count; i++) {
        if (strncmp(array.uuids[i].uuid, uuid, 8) == 0) {
            aux = array.uuids[i].number;
        }
    }

	snprintf((char*)new_command, sizeof(new_command),"%s %d, %d, %s", AT_GCWA, INDEX_CONNECTION, aux, string);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

	return result;
}

status_t ble_connect_encrypt(uint8_t connection_index)
{
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL] = {0};
	status_t result;
	snprintf((char*)new_command, sizeof(new_command),"%s %d", AT_LENC, connection_index);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

	return result;
}

status_t ble_delete_bond_database(){

	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
	status_t result;

	snprintf((char*)new_command, sizeof(new_command), "%s", AT_BNDX);
	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

	return result;
}

status_t ble_delete_bond_address(char address[MAX_DEVICE_ADDRESS_LENGTH]){

	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
	status_t result;

	snprintf((char*)new_command, sizeof(new_command),"%s %s ", AT_BNDD, address);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

	return result;
}

status_t ble_check_bond(char address[MAX_DEVICE_ADDRESS_LENGTH]){

	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
	status_t result;

	snprintf((char*)new_command, sizeof(new_command),"%s %s ", AT_BNDT, address);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

	return result;
}

status_t ble_num_devices_bond_database()
{
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
	status_t result;

	snprintf((char*)new_command, sizeof(new_command),"%s %d", ATI, 2009);

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_OK);

	if (result!=STATUS_OK){
		return result;
	}

	BLEAPI_ParseNumDevices(response, sizeof(response));

	return result;
}

status_t check_keywords_in_buffer(const uint8_t *buffer, size_t size) {
    struct {
        const char *keyword;
        status_t status;
    } keywords[] = {
        {"showcode", STATUS_SHOWCODE},
        {"comparecode", STATUS_COMPARECODE},
        {"passkey", STATUS_PASSKEY},
        {"oobkey", STATUS_OOBKEY},
        {"lescoob", STATUS_LESCOOB},
        {"xxkey", STATUS_XXKEY},
		{"encrypt", STATUS_ENCRYPT}
    };
    size_t num_keywords = sizeof(keywords) / sizeof(keywords[0]);
    char buffer_copy[SIZE_BUFFER_RESPONSE_SMALL + 1];

    if (size > SIZE_BUFFER_RESPONSE_SMALL) {
        size = SIZE_BUFFER_RESPONSE_SMALL;
    }

    strncpy(buffer_copy, (const char *)buffer, size);
    buffer_copy[size] = '\0';

    for (size_t i = 0; i < num_keywords; i++) {
        if (strstr(buffer_copy, keywords[i].keyword)) {
            return keywords[i].status;
        }
    }

    return STATUS_ERROR;
}
status_t ble_pair(uint8_t hIdx)
{
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
	status_t result;

	memset(new_command, 0, sizeof(new_command));

	snprintf((char*)new_command, sizeof(new_command),"%s %d", AT_PAIR, hIdx);
	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_AT);

	if(result != STATUS_OK){
		return result;
	}

	UART_ReceiveData(response, sizeof(response), TERMINATION_AT);
	UART_ReceiveData(response, sizeof(response), TERMINATION_AT);

	result = check_keywords_in_buffer(response, sizeof(response));

	return result;
}

status_t ble_pair_passkey(uint32_t passkey, uint8_t hIdx){

	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
	status_t result;

	memset(new_command, 0, sizeof(new_command));

	snprintf((char*)new_command, sizeof(new_command),"%s %d, %d", AT_PRSP, hIdx, passkey);
	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_AT);

	return result;
}

status_t ble_set_passkey(uint32_t *passkey){

	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE_SMALL];
	status_t result;

	if (BLE_ValidPasskey(passkey) == STATUS_NO_VALID_PASSKEY){
		return STATUS_ERROR;
	}

	memset(new_command, 0, sizeof(new_command));

	snprintf((char*)new_command, sizeof(new_command),"%s %d", AT_PKEY, passkey);
	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_AT);

	return result;
}


status_t reader(ble_device_info_t *device, uint8_t *buffer_reader){

	uint8_t response[SIZE_BUFFER_RESPONSE];
	status_t result = 0;

	memset(response, 0, SIZE_BUFFER_RESPONSE);

	while(result != STATUS_OK){
	result = UART_ReceiveData(response, sizeof(response), TERMINATION_AT);
	}
	result = Parser(response, sizeof(response), device, buffer_reader);
	return result;
}

status_t reader_client(ble_device_info_t *device, uint8_t *buffer_reader){

	uint8_t response[SIZE_BUFFER_RESPONSE];
	status_t result = 0;

	memset(response, 0, SIZE_BUFFER_RESPONSE);

	while(result != STATUS_OK){
		result = UART_ReceiveData(response, sizeof(response), TERMINATION_AT);
	}
	result = Parser(response, sizeof(response), device, buffer_reader);
	return result;
}

status_t ble_init_characteristic_params(ble_characteristic_params_t *params, uint8_t uuidindex, uint8_t *uuid,
										prop_characteristic properties, uint8_t metadataIndex, uint8_t cccdIndex,
										uint8_t sccdIndex, char* initial_value) {

    if (params == NULL || uuid == NULL) {
        return STATUS_ERROR;
    }

    memset(params->uuid, 0, sizeof(params->uuid));
    memset(params->value, 0, sizeof(params->value));

    params->uuidindex = uuidindex;
    memcpy(params->uuid, uuid, MAX_LENGTH_UUID);
    params->properties = properties;
    params->metadataIndex = metadataIndex;
    params->cccdIndex = cccdIndex;
    params->sccdIndex = sccdIndex;
    params->id_gscb = 0;
    params->initial_value = initial_value;
    params->notify = false;
    params->indicate = false;

    return STATUS_OK;
}

status_t ble_init_service_info(ble_service_info_t *service_info, const uint8_t *uuid, uint8_t index,
                               security_service_level rdRights, security_service_level wrRights, uint16_t len_Data) {
    if (!service_info || !uuid) {
        return STATUS_ERROR;
    }

    if(len_Data < 120){
        service_info->len_Data = len_Data;
    }
    else{
    	return STATUS_ERROR;
    }
    service_info->open = true;
    memcpy(service_info->uuid, uuid, MAX_LENGTH_UUID);
    service_info->index = index;
    service_info->rdRights = rdRights;
    service_info->wrRights = wrRights;

    memset(service_info->characteristics, 0, sizeof(service_info->characteristics));
    service_info->characteristics_count = 0;

    return STATUS_OK;
}

status_t ble_notify(const char* uuid){

	uint8_t aux = 0;
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE] = {0};
	status_t result;

	for (uint8_t i = 0; i < array.count; i++) {
	        if (strncmp(array.uuids[i].uuid, uuid, 8) == 0) {
	            aux = array.uuids[i].number;
	        }
	    }

	aux = aux + 1;

	snprintf((char*)new_command, sizeof(new_command), "%s %d, %d, %s", AT_GCWA, 1, aux, "0100");

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_AT);

	return result;
}

status_t ble_indicate(const char* uuid){

	uint8_t aux = 0;
	uint8_t new_command[SIZE_BUFFER_COMMAND];
	uint8_t response[SIZE_BUFFER_RESPONSE] = {0};
	status_t result;

	for (uint8_t i = 0; i < array.count; i++) {
	        if (strncmp(array.uuids[i].uuid, uuid, 8) == 0) {
	            aux = array.uuids[i].number;
	        }
	    }

	aux = aux + 1;

	snprintf((char*)new_command, sizeof(new_command),"%s %d, %d, %s", AT_GCWA, 1, aux, "0101");

	result = sendATCommandAndCheckResponse((const char*)new_command, response,sizeof(response),TERMINATION_AT);

	return result;
}
