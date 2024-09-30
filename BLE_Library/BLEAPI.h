#ifndef BLEAPI_H
#define BLEAPI_H

#include <stdbool.h>
#include <stdint.h>
#include "enumtype.h"

#define DEVICE_ADDRESS_LENGTH 15
#define MAX_SIZE_VALUE 120
#define MAX_CHARACTERISTIC_DATA_LENGTH 20
#define MAX_SERVICE_CHARACTERISTICS 10
#define MAX_SCAN_DEVICES 10
#define MAX_DEVICE_ADDRESS_LENGTH 14
#define MAX_ADDRESS_LENGTH 17
#define MAX_DEVICE_NAME_LENGTH 128
#define MAX_RESULT_COUNT 100
#define MAX_SERVICES 20
#define MAX_LENGTH_UUID 128
#define MAX_DATA_CH 120
#define LENGTH_UUID 6
#define MAX_UUID_COUNT 128
#define UUID_RETURN "FE01180F"
#define INDEX_CONNECTION 1
#define SIZE_BUFFER_RESPONSE 1000
#define SIZE_BUFFER_RESPONSE_SMALL 100
#define SIZE_BUFFER_COMMAND 150
#define SIZE_BUFFER_EXTRA 20000
#define OFFSET_VALUE 0
#define MAX_PASS_LENGTH 6
#define MAX_CONNECTIONS 1
#define MAX_CHARACTERISTICS 50
#define MAX_DATA_SERVICE 252

/**
 * @struct ble_characteristic_params_t
 * @brief Structure to define the parameters of a BLE characteristic.
 *
 * @var ble_characteristic_params_t::uuidindex
 * Index for the UUID of the characteristic.
 *
 * @var ble_characteristic_params_t::uuid
 * The UUID of the characteristic.
 *
 * @var ble_characteristic_params_t::properties
 * The properties of the characteristic (e.g., read, write, notify).
 *
 * @var ble_characteristic_params_t::metadataIndex
 * Index of the metadata associated with the characteristic.
 *
 * @var ble_characteristic_params_t::cccdIndex
 * Index for Client Characteristic Configuration Descriptor.
 *
 * @var ble_characteristic_params_t::sccdIndex
 * Index for Server Characteristic Configuration Descriptor.
 *
 * @var ble_characteristic_params_t::id_gscb
 * Identifier for Generic Service Change Block.
 *
 * @var ble_characteristic_params_t::initial_value
 * Pointer to the initial value of the characteristic.
 */
typedef struct {
    uint8_t uuidindex;
    uint8_t uuid[MAX_LENGTH_UUID];
    prop_characteristic properties;
    uint8_t metadataIndex;
    uint8_t cccdIndex;
    uint8_t sccdIndex;
    uint8_t id_gscb;
    char* initial_value;
    uint8_t value[MAX_DATA_CH];
    bool notify;
    bool indicate;
} ble_characteristic_params_t;


typedef struct {
    uint8_t hIdx;
    char address[MAX_ADDRESS_LENGTH + 1];
    bool Vsp;
} ConnectionInf;

typedef struct {
    uint8_t uuid[MAX_LENGTH_UUID];
    uint8_t index;
    security_service_level rdRights;
    security_service_level wrRights;
    uint16_t len_Data;
    ble_characteristic_params_t characteristics[MAX_CHARACTERISTICS];
    uint8_t characteristics_count;
    bool open;
} ble_service_info_t;

typedef struct {
    bool advertisment;
    bool is_connected;
    ble_service_info_t services[MAX_SERVICES];
    uint8_t services_count;
    ConnectionInf connections[MAX_CONNECTIONS];
    uint8_t connections_count;
} ble_device_info_t;

/**
 * @brief Initialize GATT server for a BLE device
 *
 * This function initializes the Generic Attribute (GATT) server for the specified BLE device.
 *
 * @param device_info Pointer to the BLE device information structure
 */
void ble_initialize_gatt_server(ble_device_info_t *device_info);

/**
 * @brief Helper function, sends the AT command and reads response
 *
 * This function sends an AT command and receives the response 
 *
 * @retval status, indicating success or error.
 */

status_t sendATCommandAndCheckResponse(const char *ATCommand, uint8_t *response,uint32_t size, char *expectedTermination);


/**
 * @brief Save the current BLE parameters
 *
 * @param none
 *
 * @retval status The status of the save operation, indicating success or error
 */
status_t ble_save();

/**
 * @brief Resets the BLE module.
 *
 * This function resets the BLE module, returning it to its initial state.
 *
 * @retval status The status of the BLE reset operation, indicating success or error.
 */
status_t ble_reset();

/**
 * @brief Sets default parameters for the BLE module.
 *
 * This function sets default parameters for the BLE module, configuring it to default settings.
 *
 * @retval status The status of setting default parameters, indicating success or error.
 */
status_t ble_default_parameters();

/**
 * @brief Configures the input/output (IO) capability parameters for BLE communication.
 *
 * This function sets the parameters that determine how input/output capabilities will be handled
 * during the BLE pairing process. IO parameters include settings such as input/output types,
 * security levels, and display options.
 *
 * @param value Configuration parameter defining the IO capabilities. This parameter should be of type
 *        `IO_Parameter`, which specifies the desired options for BLE communication.
 *
 * @retval status The status of the configuration operation. Returns an error code indicating the reason
 *         for failure, or 0 if the configuration was successful.
 */
status_t ble_io_capability_parameter(IO_Parameter value);

/**
 * @brief Initialize the BLE stack and adapter
 *
 * @param none
 *
 * @retval status The status of the initialization process, indicating success or error
 */
status_t ble_init(ble_device_info_t *device);

/**
 * @brief Connect to a device using its MAC address
 *
 * @param none
 *
 * @retval status The status of the connection attempt, indicating success or error
 */
status_t ble_connect();

/**
 * @brief Listen for incoming BLE connections
 *
 * @param connection type ConnectionInfo
 *
 * @retval status The status of the listening state, indicating if it's ready to accept connections or not
 */
status_t ble_listening(ConnectionInf connection,ble_device_info_t *device);
/**
 * @brief Start scanning for BLE devices
 *
 * @param none
 *
 * @retval status The status of the scan starting process, indicating success or error
 */
status_t ble_start_scan();

/**
 * @brief Initialize BLE Generic Service Server (GSSB)
 *
 * This function initializes the BLE Generic Service Server (GSSB) for the specified service.
 *
 * @param service Pointer to the BLE service information structure
 *
 * @retval status The status of the initialization operation. Returns an error code
 *         indicating the reason for failure, or 0 if initialization was successful.
 */
status_t ble_gssb(ble_service_info_t *service);


/**
 * @brief Stop scanning for BLE devices
 *
 * @param none
 *
 * @retval status The status of the scan stopping process, indicating success or error
 */
status_t ble_stop_scan();

/**
 * @brief Handle the state of BLE advertisements
 *
 * @param command Pointer to the string containing the advertisement command
 * @param advertismentState Boolean state representing whether to enable or disable advertising
 *
 * @retval status The status of handling the advertisement, indicating success or error
 */
status_t ble_handle_advertisment(const char* command, bool advertismentState, ble_device_info_t *device);


/**
 * @brief Wait for an incoming BLE connection in advertisement mode
 *
 * @param none
 *
 * @retval status The status of the operation, indicating if the device successfully entered advertisement mode
 */
status_t ble_start_advertisment(ble_device_info_t *device);

/**
 * @brief Stop BLE advertisement mode
 *
 * @param none
 *
 * @retval status The status of the operation, indicating if the device successfully exited advertisement mode
 */
status_t ble_stop_advertisment(ble_device_info_t *device);

/**
 * @brief Create a BLE service
 *
 * @param service Pointer to the structure containing information about the service to create
 *
 * @retval status The status of the service creation, indicating success or error
 */
status_t ble_create_service(ble_service_info_t *service, ble_device_info_t *device);

/**
 * @brief End a BLE service
 *
 * @param none
 *
 * @retval status The status of the operation, indicating if the service was successfully ended
 */
status_t ble_end_service(ble_service_info_t *service, ble_device_info_t *device);

/**
 * @brief Start a characteristic within a BLE service
 *
 * @param characteristic Pointer to the characteristic parameters structure to start the characteristic
 *
 * @retval status The status of the characteristic start operation, indicating success or error
 */
status_t ble_start_characteristic(ble_characteristic_params_t *characteristic, ble_service_info_t *service);

/**
 * @brief Change the value of a BLE characteristic
 *
 * @param characteristic Pointer to the characteristic parameters structure where the value should be changed
 * @param value The new value to set for the characteristic
 *
 * @retval status The status of the value change operation, indicating success or error
 */
status_t ble_change_value_characteristic(ble_characteristic_params_t *characteristic, char* value, prop_characteristic type);

/**
 * @brief Read the value of a BLE characteristic
 *
 * @param hAttr Pointer to the attribute handle of the characteristic to read
 *
 * @retval status The status of the read operation, indicating success or error
 */
/**
 * @brief Retrieve the value of a characteristic using the GCTM (Get Characteristic by UUID Tuple Members) method
 *
 * @param none
 *
 * @retval status The status of the operation, indicating success or error
 */
status_t ble_gctm();

/**
 * @brief Read the value of a characteristic from a BLE device, specified by UUID
 *
 * @param uuid Pointer to the string containing the UUID of the characteristic to read
 * @param value Pointer to the buffer where the characteristic value will be stored
 * @param valueSize The size of the buffer provided for the value
 *
 * @retval status The status of the read operation, indicating success or error
 */
status_t ble_client_read_characteristic(const char* uuid, uint8_t *value, uint32_t valueSize, uint16_t Offset);
/**
 * @brief Write a string to a characteristic of a BLE device, specified by UUID
 *
 * @param uuid Pointer to the string containing the UUID of the characteristic to write to
 * @param string Pointer to the string that will be written to the characteristic
 *
 * @retval status The status of the write operation, indicating success or error
 */
status_t ble_client_write_characteristic(const char* uuid, char* string);


/**
 * @brief Checks if the device is currently connected to a BLE device.
 *
 * This function checks whether the device is currently connected to a BLE device.
 *
 * @return true if the device is connected to a BLE device, false otherwise.
 */
bool ble_is_connected();

/**
 * @brief Initiates encryption for the specified BLE connection.
 *
 * This function initiates encryption for the BLE connection identified by the given connection index.
 *
 * @param connection_index The index of the BLE connection for which encryption is to be initiated.
 *
 * @retval status The status of the encryption initiation operation, indicating success or error.
 */
status_t ble_connect_encrypt(uint8_t connection_index);

/**
 * @brief Responds to a BLE pairing confirmation request.
 *
 * This function sends a confirmation response to a pairing request for the specified BLE connection.
 *
 * @param connection_index The index of the BLE connection for which the pairing confirmation is requested.
 * @param answer The response to the pairing confirmation request.
 *
 * @retval status The status of the pairing confirmation response operation, indicating success or error.
 */
status_t ble_pair_confirmation(uint8_t connection_index, pair_confirmation answer);

/**
 * @brief Deletes the bond database for BLE devices.
 *
 * This function deletes the bond database storing pairing information for BLE devices.
 *
 * @retval status The status of the bond database deletion operation.
 */
status_t ble_delete_bond_database();

/**
 * @brief Deletes the bond information associated with a specific BLE device address.
 *
 * This function deletes the bond information associated with the specified BLE device address from the bond database.
 *
 * @param address The BLE device address to delete bond information for.
 *
 * @retval status The status of the bond address deletion operation.
 */
status_t ble_delete_bond_address(char address[MAX_DEVICE_ADDRESS_LENGTH]);

/**
 * @brief Initiates pairing with a BLE device using a passkey.
 *
 * This function initiates the pairing process with a BLE device using the provided passkey.
 *
 * @param passkey Pointer to the passkey string used for pairing.
 *
 * @retval status The status of the pairing initiation operation.
 */
status_t ble_pair_passkey(uint32_t passkey, uint8_t hIdx);

/**
 * @brief Read data from the buffer and parse in function of the header.
 *
 * This function reads data from a BLE device into the provided buffer.
 *
 * @param device Pointer to the BLE device information structure
 * @param buffer_reader Pointer to the buffer where read data will be stored
 *
 * @retval status The status of the read operation. Returns an error code
 *         indicating the reason for failure, or 0 if reading was successful.
 */

/**
 * @brief Initialize BLE characteristic parameters
 *
 * This function initializes the parameters for a BLE characteristic.
 *
 * @param params Pointer to the structure holding BLE characteristic parameters
 * @param uuidindex Index of the UUID for the characteristic
 * @param uuid Pointer to the UUID data
 * @param properties Properties of the characteristic
 * @param metadataIndex Index of the metadata
 * @param cccdIndex Index of the Client Characteristic Configuration Descriptor (CCCD)
 * @param sccdIndex Index of the Server Characteristic Configuration Descriptor (SCCD)
 * @param initial_value Pointer to the initial value of the characteristic
 *
 * @retval status The status of the initialization operation. Returns an error code
 *         indicating the reason for failure, or 0 if initialization was successful.
 */
status_t ble_init_characteristic_params(ble_characteristic_params_t *params, uint8_t uuidindex, uint8_t *uuid,
                                        prop_characteristic properties, uint8_t metadataIndex, uint8_t cccdIndex,
                                        uint8_t sccdIndex, char* initial_value);

/**
 * @brief Initialize BLE service information
 *
 * This function initializes the information for a BLE service.
 *
 * @param service_info Pointer to the structure holding BLE service information
 * @param uuid Pointer to the UUID data of the service
 * @param index Index of the service
 * @param rdRights Security level for read access
 * @param wrRights Security level for write access
 * @param len_Data Length of the service data
 *
 * @retval status The status of the initialization operation. Returns an error code
 *         indicating the reason for failure, or 0 if initialization was successful.
 */
status_t ble_init_service_info(ble_service_info_t *service_info, const uint8_t *uuid, uint8_t index,
                               security_service_level rdRights, security_service_level wrRights, uint16_t len_Data);

/**
 * @brief Initiates the BLE pairing process with a specified device.
 *
 * This function starts the pairing process with a Bluetooth Low Energy (BLE) device identified by its
 * handle index. The handle index is used to select the device with which to establish a pairing.
 *
 * @param hIdx Handle index of the BLE device to pair with. This index identifies the target device
 *        in the pairing process.
 *
 * @retval status The status of the pairing operation. Returns an error code indicating the reason
 *         for failure, or 0 if the pairing process was successfully initiated.
 */
status_t ble_pair(uint8_t hIdx);

/**
 * @brief Activate notification of a service from a server
 *
 * This function notifies BLE clients with a characteristic value identified by its UUID.
 *
 * @param uuid Pointer to the UUID string identifying the characteristic
 *
 * @retval status The status of the notification operation. Returns an error code
 *         indicating the reason for failure, or 0 if notification was successful.
 */
status_t ble_notify(const char* uuid);

/**
 * @brief Activate indication of a service from a server
 *
 * This function notifies BLE clients with a characteristic value identified by its UUID.
 *
 * @param uuid Pointer to the UUID string identifying the characteristic
 *
 * @retval status The status of the notification operation. Returns an error code
 *         indicating the reason for failure, or 0 if notification was successful.
 */
status_t ble_indicate(const char* uuid);

/**
 * @brief Reads data from a BLE device and stores it in a buffer.
 *
 * This function interacts with a Bluetooth Low Energy (BLE) device to read data and
 * then stores the retrieved data into the provided buffer. The function uses information
 * from the `device` structure to identify the BLE device from which to read.
 *
 * @param device Pointer to a `ble_device_info_t` structure containing information about the BLE device.
 *        This includes the necessary details required to perform the read operation.
 * @param buffer_reader Pointer to a buffer where the read data will be stored. This buffer should
 *        be large enough to hold the data being read from the BLE device.
 *
 * @retval status The status of the read operation. Returns an error code indicating the reason
 *         for failure, or 0 if the data was successfully read and stored in the buffer.
 */

status_t ble_set_passkey(uint32_t *passkey);

#endif /* BLEAPI_H */
