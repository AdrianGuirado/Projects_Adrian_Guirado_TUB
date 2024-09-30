#ifndef STATUS_ENUM_H
#define STATUS_ENUM_H

typedef enum {
    STATUS_ERROR = 1,                        // Generic error
    STATUS_OK = 2,                           // Operation successful
    STATUS_ERROR_UUID = 3,                   // UUID error
    STATUS_NOT_CONNECTED = 4,                // Not connected
    STATUS_NO_DEVICE = 5,                    // Device not found
    STATUS_RESOURCES = 6,                    // Resource error
    STATUS_DISCONNECTED = 7,                 // Disconnected
    STATUS_CONNECTED = 8,                    // Connected
    STATUS_TIMEOUT = 9,                      // Timeout
    STATUS_SEND_ERROR = 10,                  // Send error
    STATUS_RECEIVE_ERROR = 11,               // Receive error
    STATUS_READ = 12,                        // Read error
    STATUS_BONDED = 13,                      // Bonded
    STATUS_NO_BONDED = 14,                   // Not bonded
    STATUS_SCANEND = 15,                     // Scan ended
    STATUS_MEMORY_ERROR = 16,                // Memory error
    STATUS_NO_VALID = 17,                    // Not valid
    STATUS_NO_VALID_PASSKEY = 18,            // No valid passkey
    STATUS_ERROR_XX = 19,                    // Generic error (placeholder)
    STATUS_ERROR_01 = 101,                   // Invalid parameter number
    STATUS_ERROR_02 = 102,                   // Value supplied is out of range
    STATUS_ERROR_05 = 105,                   // Syntax error
    STATUS_ERROR_09 = 109,                   // Invalid address has been supplied
    STATUS_ERROR_14 = 114,                   // Command cannot be processed in current state
    STATUS_ERROR_15 = 115,                   // Unknown command
    STATUS_ERROR_33 = 133,                   // Value supplied is not valid
    STATUS_ERROR_46 = 146,                   // GPIO specified is not available
    STATUS_ERROR_47 = 147,                   // Too few parameters supplied
    STATUS_ERROR_48 = 148,                   // Too many parameters supplied
    STATUS_ERROR_49 = 149,                   // Hex string is not valid
    STATUS_ERROR_50 = 150,                   // Save fail
    STATUS_ERROR_51 = 151,                   // Restore fail
    STATUS_ERROR_52 = 152,                   // VSP open fail
    STATUS_ERROR_53 = 153,                   // Invalid advert type
    STATUS_ERROR_54 = 154,                   // Invalid UUID
    STATUS_ERROR_55 = 155,                   // Service not ended
    STATUS_ERROR_56 = 156,                   // Characteristic not ended
    STATUS_ERROR_57 = 157,                   // Service not started
    STATUS_ERROR_58 = 158,                   // Too many characteristics
    STATUS_ERROR_59 = 159,                   // Characteristic not started
    STATUS_ERROR_62 = 162,                   // Directed advert but peer address is missing
    STATUS_ERROR_63 = 163,                   // Invalid channel mask
    STATUS_ERROR_64 = 164,                   // Invalid advert reports
    STATUS_ERROR_65 = 165,                   // Invalid advert report data
    STATUS_ERROR_66 = 166,                   // Invalid advert report data size
    STATUS_ERROR_67 = 167,                   // Invalid out of band (OOB) data
    STATUS_ERROR_68 = 168,                   // Newline character was expected but was not found
    STATUS_ERROR_71 = 171,                   // I2C pin already defined
    STATUS_ERROR_74 = 174,                   // PWM channel overflow
    STATUS_ERROR_75 = 175,                   // Wrong GPIO type
    STATUS_ERROR_77 = 177,                   // Frequency channel overflow
    STATUS_ERROR_79 = 179,                   // AD channel overflow
    STATUS_ERROR_82 = 182,                   // Command pin state incorrect
    STATUS_ERROR_99 = 199,                    // Functionality not coded
	STATUS_NOTIFICATION = 200,
	STATUS_SHOWCODE = 201,
	STATUS_PASSKEY = 202,
	STATUS_XXKEY = 203,
	STATUS_LESCOOB = 204,
	STATUS_COMPARECODE = 205,
	STATUS_OOBKEY = 206,
	STATUS_ENCRYPT = 207
} status_t;


typedef enum{
 S_MPHY = 1,
 S_CODED = 2,
 S_SECOND = 4,
 S_PASSIVE = 8
} ScanType_t;

typedef enum{
	S_VSP = 1,
	S_ADVERTISMENT_NO_TIMEOUT = 2,
	S_SCANNING_NO_TIMEOUT = 4,
	S_MAX_THROUGHPUT = 8,
	S_EXTENDED_ADVERTISMENT = 16, // must be set when using LE coded PHY or 2MPHY
	S_PHYRATE_1MPHY = 0,
	S_PHY_RATE_LONGRANGE = 32,
	S_PHY_RFU = 64,
	S_PHY_2MPHY = 96
}VSPconfig_t;




typedef enum {
    BROADCAST_CAPABLE_CH = 1,  // Sccd descriptor must be present
    READABLE_BY_CLIENT_CH = 2,      // Can be read by the client
    WRITABLE_WITHOUT_ACK_CH = 4,    // Can be written by the client without an ACK
    WRITABLE_WITH_ACK_CH = 8,       // Can be written (ACK is sent back)
    NOTIFIABLE_CH = 16,              // Cccd descriptor must be present
    INDICATABLE_CH = 32
} prop_characteristic;

typedef enum {
    PREFER_OOB = -1,  // Prefer Out-of-Band
    JUST_WORKS = 0,   // Just Works
    DISPLAY_YN = 1,   // Display with Yes/No
    KEYBOARD_ONLY = 2,// Keyboard only
    DISPLAY_ONLY = 3, // Display Only
    KEYBOARD_DISPLAY = 4 // Keyboard + Display
} IO_Parameter;// IO_Capability

typedef enum {
	NO_ACCESS = 0,
	OPEN = 1,
	ENCRYPTED_NO_MITM = 2,
	ENCRYPTED_MITM = 3
}security_service_level;

typedef enum {
	DEFAULT_FORMAT = 0,
	NO_DEFAULT_FORMAT = 1
}scanning_format;

typedef enum {
	ACCEPT = 1,
	DECLINE = 0
}pair_confirmation;

typedef enum {
    HEADER_TM,
    HEADER_ADAD,
    HEADER_TXPO,
    HEADER_CSEC,
    HEADER_AB,
    HEADER_AD,
    HEADER_AE,
    HEADER_AK,
    HEADER_AR,
    HEADER_AS,
    HEADER_AW,
    HEADER_CC,
    HEADER_FC,
    HEADER_FD,
    HEADER_IN,
    HEADER_SC,
    HEADER_WR,
    HEADER_LL,
    HEADER_PI,
    HEADER_SR,
    HEADER_CP,
    HEADER_MT,
    HEADER_PU,
    HEADER_PF,
    HEADER_CONNECT,
    HEADER_ERROR,
    HEADER_NOCARRIER,
    HEADER_RING,
    HEADER_SHOWCODE,
    HEADER_COMPARECODE,
    HEADER_SCANNED,
    HEADER_DISCON,
    HEADER_ENCRYPT,
    HEADER_PASSKEY,
    HEADER_OOBKEY,
    HEADER_LESCOOB,
    HEADER_XXKEY,
    HEADER_UNKNOWN
} HeaderType;


#endif // STATUS_ENUM_H
