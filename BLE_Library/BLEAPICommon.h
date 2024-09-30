#ifndef BLEAPICOMMON_H
#define BLEAPICOMMON_H

#include <stdint.h>
#include "main.h"
#include "enumtype.h"
#include "BLEAPI.h"

#define MAX_UUID_COUNT 128
#define UUID_RET 8

#define AT_COMMAND(command) ("AT+" #command)
#define ATS_COMMAND(number) ("ATS " #number " =")

#define AT "AT"

#define VSP 1 // 1 if VSP is activated 0 if not

// Parameter access commands
#define AT_S
#define AT_F "AT&F"
#define AT_W "AT&W"
#define ATI "ATI"
#define ATS "ATS"

#define ATS_107 ATS_COMMAND(107)

// Bond database commands
#define AT_BNDD AT_COMMAND(BNDD)
#define AT_BNDT AT_COMMAND(BNDT)
#define AT_BNDX AT_COMMAND(BNDX)

// GATT Client commands
#define AT_GCTM AT_COMMAND(GCTM)
#define AT_GCFA AT_COMMAND(GCFA)
#define AT_GCRD AT_COMMAND(GCRD)
#define AT_GCWA AT_COMMAND(GCWA)
#define AT_GCWC AT_COMMAND(GCWC)
#define AT_LMTU AT_COMMAND(LMTU)

// GATT Server commands
#define AT_GSMD AT_COMMAND(GSMD)
#define AT_GSCB AT_COMMAND(GSCB)
#define AT_GSCE AT_COMMAND(GSCE)
#define AT_GSSB AT_COMMAND(GSSB)
#define AT_GSSE AT_COMMAND(GSSE)
#define AT_GSIC AT_COMMAND(GSIC)
#define AT_GSNO AT_COMMAND(GSNO)
#define AT_GSWC AT_COMMAND(GSWC)
#define AT_UUID AT_COMMAND(UUID)

// I2C commands
#define AT_I2R AT_COMMAND(I2R)
#define AT_I2W AT_COMMAND(I2W)

// Advertising commands
#define AT_AARA AT_COMMAND(AARA)
#define AT_ACMT AT_COMMAND(ACMT)
#define AT_ADAD AT_COMMAND(ADAD)
#define AT_ARST AT_COMMAND(ARST)
#define AT_ASRA AT_COMMAND(ASRA)
#define AT_LADV AT_COMMAND(LADV)
#define AT_LADVX AT_COMMAND(LADVX)
#define AT_EADV AT_COMMAND(EADV)

// Connection commands
#define AT_CSEC AT_COMMAND(CSEC)
#define AT_LCON AT_COMMAND(LCON)
#define AT_LDSC AT_COMMAND(LDSC)
#define AT_LENC AT_COMMAND(LENC)

// Scanning commands
#define AT_SMFT AT_COMMAND(SMFT)
#define AT_LSCN AT_COMMAND(LSCN)
#define AT_LSCNX AT_COMMAND(LSCNX)

// GAP commands
#define AT_LPHY AT_COMMAND(LPHY)

// Pairing commands
#define AT_PAIR AT_COMMAND(PAIR)
#define AT_PCFG AT_COMMAND(PCFG)
#define AT_PCNF AT_COMMAND(PCNF)
#define AT_PKEY AT_COMMAND(PKEY)
#define AT_PRSP AT_COMMAND(PRSP)

// Out of Band Pairing Commands
#define AT_OOBL AT_COMMAND(OOBL)
#define AT_OOBR AT_COMMAND(OOBR)

// SIO commands
#define AT_SIOC AT_COMMAND(SIOC)
#define AT_SIOR AT_COMMAND(SIOR)
#define AT_SIOW AT_COMMAND(SIOW)

// SPI commands
#define AT_SPR AT_COMMAND(SPR)
#define AT_SPW AT_COMMAND(SPW)

// Transmit Power commands
#define AT_TXPO AT_COMMAND(TXPO)

// User Function commands
#define AT_UFU AT_COMMAND(UFU)

// VSP commands
#define ATD "ATD\r"
#define AT_LVSP AT_COMMAND(LVSP)

// Module Management commands
#define ATZ "ATZ"

// Fixed Variables

#define MAX_DEVICE_ADDRESS_LENGTH 14
#define MAX_ADDRESS_LENGTH 17
#define COMMAND_BUFFER_SIZE 50
#define UUID_NUMBER 8
// Parameters

// Termination parameters

#define TERMINATION_AT "\r"
#define TERMINATION_OK "\nOK\r"
#define TERMINATION_SCAN "scanend"
#define TERMINATION_NONE
#define TERMINATION_CONTINUE "continue"
#define TERMINATION_GSCE "\r\nOK\r"
#define TERMINATION_CONNECT "connect"
#define TERMINATION_READ "AR"
#define TERMINATION_CHECK "check"
#define TERMINATION_OTHER "\t"



/**
 * @struct device_scanned_characteristics
 * @brief Structure to represent the characteristics of a scanned BLE device.
 *
 * @var device_scanned_characteristics::RSSI
 * The Received Signal Strength Indicator (RSSI) value for the device.
 *
 * @var device_scanned_characteristics::address
 * The MAC address of the device.
 *
 * @var device_scanned_characteristics::name
 * The name of the device.
 */
typedef struct {
    int RSSI;
    char address[MAX_DEVICE_ADDRESS_LENGTH];
    char name[MAX_DEVICE_NAME_LENGTH];
} device_scanned_characteristics;

/**
 * @struct ble_scan_results
 * @brief Structure to hold the results of a BLE scan.
 *
 * @var ble_scan_results::count
 * The number of devices found in the scan.
 *
 * @var ble_scan_results::devices
 * An array of `device_scanned_characteristics` structures, each representing a scanned device.
 */
typedef struct {
    int count;
    device_scanned_characteristics devices[128];
} ble_scan_results;

/**
 * @struct UUIDProcessed
 * @brief Structure for processed UUID information.
 *
 * @var UUIDProcessed::uuid
 * The UUID as a character array.
 *
 * @var UUIDProcessed::number
 * A number associated with the UUID (e.g., an index or identifier).
 */
typedef struct {
    char uuid[UUID_RET];
    uint8_t number;
} UUIDProcessed;

/**
 * @struct UUIDArray
 * @brief Structure to hold an array of processed UUIDs.
 *
 * @var UUIDArray::uuids
 * An array of `UUIDProcessed` structures.
 *
 * @var UUIDArray::count
 * The number of UUIDs stored in the array.
 */
typedef struct {
    UUIDProcessed uuids[MAX_UUID_COUNT];
    size_t count;
} UUIDArray;

// UART Management Functions

// Function to initialize the UART peripheral
/**
 * @brief Initialize the UART interface
 *
 * @param none
 *
 * @retval none
 */
void UART_Init();

/**
 * @brief Send data over UART
 *
 * @param data Pointer to the array of data to be sent
 * @param size The number of bytes to send
 *
 * @retval status The status of the send operation
 */
uint8_t UART_SendData(const uint8_t *data, uint32_t size);

/**
 * @brief Receive data over UART
 *
 * @param buffer Pointer to the buffer where received data will be stored
 * @param size The number of bytes to receive
 * @param terminationChar Pointer to the character that determines the end of data reception
 *
 * @retval status The status of the receive operation
 */
uint8_t UART_ReceiveData(uint8_t *buffer, uint32_t size, char* terminationChar);

/**
 * @brief Error handler function
 *
 * This function handles errors with the provided buffer.
 *
 * @param buffer Pointer to the buffer containing error information
 *
 * @retval status The status of the error handling operation. Returns an error code
 *         indicating the reason for failure, or 0 if successful.
 */
status_t ErrorHandler(uint8_t *buffer);

/**
 * @brief Send an AT command over UART
 *
 * @param command Pointer to the string containing the AT command to be sent
 *
 * @retval status The status of the command send operation
 */
uint8_t BLEAPI_SendATCommand(const char* command);

/**
 * @brief Receive an AT command response over UART
 *
 * @param response_buffer Pointer to the buffer where the AT command response will be stored
 *
 * @retval status The status of the receive operation
 */
uint8_t BLEAPI_CheckATResponse(uint8_t* response_buffer);

/**
 * @brief Parse the GCTM (Get Characteristic by UUID Tuple Members) response
 *
 * @param response Pointer to the array containing the GCTM response
 *
 * @param size The number of bytes in the response
 *
 * @param array Pointer to the UUIDArray structure where the parsed UUIDs will be stored
 *
 * @retval none
 */
void BLEAPI_ParseGCTM(const uint8_t *response, uint32_t size, UUIDArray *array);

/**
 * @brief Parses the scan results received from a BLE device.
 *
 * This function parses the scan results received from a BLE device.
 *
 * @param buffer Pointer to the buffer containing the scan results data.
 * @param size The size of the buffer containing the scan results data.
 * @param test Pointer to the structure where the parsed scan results will be stored.
 */
void BLEAPI_ParseScanResults(uint8_t *buffer, uint32_t size, ble_scan_results *test);

/**
 * @brief Parse the service response
 *
 * @param response Pointer to the array containing the service response
 * @param size The number of bytes in the response
 *
 * @retval none
 */
void BLEAPI_ParseServiceResponse(const uint8_t *response, uint32_t size);

/**
 * @brief Parse the characteristic response received from a BLE device
 *
 * @param response Pointer to the array containing the characteristic response
 * @param size The number of bytes in the response
 *
 * @retval none
 */
void BLEAPI_ParseCharacteristicResponse(const uint8_t *response, uint32_t size);

/**
 * @brief Parse the GCTM (Get Characteristic by UUID Tuple Members) response
 *
 * @param response Pointer to the array containing the GCTM response
 * @param size The number of bytes in the response
 * @param array Pointer to the UUIDArray structure where the parsed UUIDs will be stored
 *
 * @retval none
 */
void BLEAPI_ParseGCTM(const uint8_t *response, uint32_t size, UUIDArray *array);

/**
 * @brief Parse a read response from a BLE device
 *
 * @param response Pointer to the buffer containing the read response
 *
 * @param size The number of bytes in the response
 *
 * @param value Pointer to the buffer where the read value will be stored
 *
 * @retval none
 */
void BLEAPI_ParseRead(uint8_t *response, uint32_t size, uint8_t *value);

/**
 * @brief Remove BLE connection for a device
 *
 * This function removes the BLE connection for the specified device using the
 * provided device information and handle index.
 *
 * @param deviceInfo Pointer to the BLE device information structure
 * @param hIdx Handle index indicating which connection to remove
 */
void BLE_RemoveConnection(ble_device_info_t *deviceInfo, uint8_t hIdx);

/**
 * @brief Parses the number of devices from a BLE response.
 *
 * This function parses the number of devices from a BLE response.
 *
 * @param response Pointer to the response buffer containing the BLE data.
 *
 * @param size The size of the response buffer.
 */
void BLEAPI_ParseNumDevices(uint8_t *response, uint32_t size);

/**
 * @brief Parses the address from a BLE response.
 *
 * This function parses the address from a BLE response.
 *
 * @param response Pointer to the response buffer containing the BLE data.
 *
 * @param size The size of the response buffer.
 *
 * @return A pointer to the parsed address string.
 */
status_t BLEAPI_ParseAddress(uint8_t *response, uint32_t size, char *outBuffer, uint32_t outBufferSize);

/**
 * @brief Finds a number by UUID in an array of UUIDs.
 *
 * This function searches for a number associated with a given UUID in an array of UUIDs.
 *
 * @param array Pointer to the array of UUIDs.
 *
 * @param uuid Pointer to the string containing the UUID to search for.
 *
 * @return The number associated with the UUID if found, otherwise -1.
 */
int find_number_by_uuid(const UUIDArray* array, const char* uuid);

/**
 * @brief Check if a device with a specific address exists in the scan results
 *
 * @param results Pointer to the ble_scan_results structure containing the results of a BLE scan
 * @param address Pointer to the string containing the address to search for
 *
 * @retval status Returns True if the device exists in the scan results, False otherwise
 */
bool BLEAPI_DeviceExists(ble_scan_results *results, char *address);

/**
 * @brief Converts a decimal string to a hexadecimal string.
 *
 * This function converts a decimal string to a hexadecimal string.
 *
 * @param decimal Pointer to the decimal string to be converted.
 *
 * @return A pointer to the hexadecimal string.
 */
void decimalToHexadecimal(const char* decimal, char* hexadecimal);


/**
 * @brief Parses the connection response received from BLE.
 *
 * This function parses the connection response received from BLE and fills the provided connection information structure.
 *
 * @param response Pointer to the buffer containing the connection response data.
 *
 * @param responseSize The size of the response buffer.
 *
 * @param connection Pointer to the structure where the parsed connection information will be stored.
 */
void BLEAPI_Parse_Connection_Response(uint8_t *response, size_t responseSize, ConnectionInf *connection);

/**
 * @brief Validate BLE passkey number
 *
 * This function validates the provided passkey number for BLE pairing.
 *
 * @param number The passkey number to validate
 *
 * @retval status The status of the passkey validation.
 */
status_t BLE_ValidPasskey(uint32_t number);

/**
 * @brief Parse CC response data
 *
 * This function parses the response data received from Command and Control (CC) module
 * for the specified BLE device.
 *
 * @param response Pointer to the response data buffer
 * @param size Size of the response data buffer
 * @param device Pointer to the BLE device information structure
 *
 * @retval status The status of the parsing operation.
 */
status_t CC_Parser(uint8_t *response, uint32_t size, ble_device_info_t *device);

/**
 * @brief Parse WR response data
 *
 * This function parses the response data received from a Write (WR) operation
 * for the specified BLE device.
 *
 * @param response Pointer to the response data buffer
 * @param size Size of the response data buffer
 * @param device Pointer to the BLE device information structure
 *
 * @retval status The status of the parsing operation.
 */
status_t WR_Parser(uint8_t *response, uint32_t size, ble_device_info_t *device);

/**
 * @brief Parse Input (IN) response data
 *
 * This function parses the response data received from an Input (IN) operation.
 *
 * @param response Pointer to the response data buffer
 * @param size Size of the response data buffer
 * @param buffer_reader Pointer to the buffer used for reading parsed data
 *
 * @retval status The status of the parsing operation. Returns an error code
 *         indicating the reason for failure, or 0 if parsing was successful.
 */
status_t IN_Parser(uint8_t *response, uint32_t size, uint8_t *buffer_reader);

/**
 * @brief Get header type from a response.
 *
 * This function determines the header type based on the provided string.
 *
 * @param header Pointer to the string containing the header
 *
 * @retval HeaderType The type of header determined from the string
 */
HeaderType getHeaderType(char *header);

/**
 * @brief Parse response data
 *
 * This function parses the response data received.
 *
 * @param response Pointer to the response data buffer
 * @param size Size of the response data buffer
 * @param device Pointer to the BLE device information structure
 * @param buffer_reader Pointer to the buffer used for reading parsed data
 *
 * @retval status The status of the parsing operation. Returns an error code
 *         indicating the reason for failure, or 0 if parsing was successful.
 */
status_t Parser(uint8_t *response, uint32_t size, ble_device_info_t *device, uint8_t *buffer_reader);

/**
 * @brief Converts a string to its hexadecimal representation.
 *
 * This function takes an input string and converts each character to its corresponding
 * two-digit hexadecimal representation. The resulting hexadecimal string is returned.
 *
 * @param input Pointer to the null-terminated string to be converted.
 *
 * @return Pointer to a newly allocated string containing the hexadecimal representation of the input.
 *         The caller is responsible for freeing this memory. If the input string is NULL or if memory
 *         allocation fails, the function returns NULL.
 */
char* stringToHex(const char* input);

/**
 * @brief Converts a byte to its hexadecimal string representation.
 *
 * This function takes a byte value and converts it to a two-character hexadecimal string.
 * The resulting string is stored in the provided buffer.
 *
 * @param byte The byte value to be converted to hexadecimal.
 * @param hex_buffer Pointer to a buffer where the resulting hexadecimal string will be stored.
 *        This buffer should be large enough to hold at least 3 characters (2 for the hex digits and 1 for the null terminator).
 *
 * @retval void This function does not return a value. It modifies the content of the `hex_buffer` to contain
 *         the hexadecimal representation of the input byte.
 *
 * @note The `hex_buffer` should be pre-allocated with at least 3 bytes of space to accommodate the two hexadecimal digits
 *       and the null terminator.
 */
void byte_to_hex(unsigned char byte, char *hex_buffer);

/**
 * @brief Sends a notification with the contents of a buffer.
 *
 * This function constructs and sends a notification using the data from the provided buffer.
 * It uses identifiers and a value string to format the notification content before sending it.
 *
 * @param buffer_reader Pointer to a constant character string that provides the base command or
 *        template for the notification. This string is used to format the final notification.
 * @param id Pointer to an array of bytes representing the identifier associated with the notification.
 * @param id_ccdd Pointer to an array of bytes representing additional identifier information (e.g., CCDD).
 * @param value Pointer to a character string that contains the value to be included in the notification.
 *
 * @retval status The status of the notification operation. Returns an error code indicating the reason
 *         for failure, or 0 if the notification was successfully sent.
 */
status_t notify_buffer(const char *buffer_reader, uint8_t *id, uint8_t *id_ccdd, char *value);


/**
 * @brief Concatenates a source array to a destination array and updates the current size.
 *
 * This function appends the contents of the source array to the end of the destination array,
 * and updates the current size of the destination array. It ensures that the destination array
 * does not exceed its allocated size.
 *
 * @param dest Pointer to the destination array where the source array will be concatenated.
 *        The destination array must be large enough to hold the concatenated result.
 * @param dest_size The total size of the destination array in bytes.
 * @param current_size Pointer to a variable representing the current size of the destination array.
 *        This value will be updated to reflect the new size after concatenation.
 * @param source Pointer to the source array to be appended to the destination array.
 * @param source_size The size of the source array in bytes.
 *
 * @retval void This function does not return a value. It modifies the destination array and
 *         updates the `current_size` parameter to reflect the new size.
 *
 * @note If the source array does not fit in the remaining space of the destination array, only
 *       the portion that fits will be copied, and the remaining part of the source array will
 *       be ignored.
 */
void concatenate_notify(uint8_t dest[], uint8_t dest_size, uint32_t *current_size, uint8_t source[], uint8_t source_size);


#endif /* BLEAPICOMMON_H */
