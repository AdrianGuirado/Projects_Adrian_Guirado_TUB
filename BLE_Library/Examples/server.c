/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.c
  * @brief          : Main program body
  ******************************************************************************
  * @attention
  *
  * Copyright (c) 2023 STMicroelectronics.
  * All rights reserved.
  *
  * This software is licensed under terms that can be found in the LICENSE file
  * in the root directory of this software component.
  * If no LICENSE file comes with this software, it is provided AS-IS.
  *
  ******************************************************************************
  */
/* USER CODE END Header */
/* Includes ------------------------------------------------------------------*/
#include "main.h"
#include "../blelaird/BLEAPICommon.h"
#include "../blelaird/BLEAPI.h"
#include "../blelaird/uuid.h"
#include <stdio.h>

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */

/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */

/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
/* USER CODE BEGIN PD */

/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/

/* USER CODE BEGIN PV */

/* USER CODE END PV */

/* Private function prototypes -----------------------------------------------*/
void SystemClock_Config(void);
static void MX_GPIO_Init(void);
static void MX_USART1_UART_Init(void);
/* USER CODE BEGIN PFP */

/* USER CODE END PFP */

/* Private user code ---------------------------------------------------------*/
/* USER CODE BEGIN 0 */

/* USER CODE END 0 */

/**
  * @brief  The application entry point.
  * @retval int
  */
int main(void)
{
  /* USER CODE BEGIN 1 */

  /* USER CODE END 1 */

  /* MCU Configuration--------------------------------------------------------*/

  /* Reset of all peripherals, Initializes the Flash interface and the Systick. */
  HAL_Init();

  /* USER CODE BEGIN Init */

  /* USER CODE END Init */

  /* Configure the system clock */
  SystemClock_Config();

  /* USER CODE BEGIN SysInit */

  /* USER CODE END SysInit */

  /* Initialize all configured peripherals */
  MX_GPIO_Init();
  MX_USART1_UART_Init();
  /* USER CODE BEGIN  */

  HAL_Delay(10000);

  // Initialize server

  ble_device_info_t server;
  ble_initialize_gatt_server(&server);

  // Initialize battery service and its characteristics

  ble_service_info_t battery;
  uint8_t serviceuuid[MAX_LENGTH_UUID];
  memset(serviceuuid, 0 , sizeof(serviceuuid));
  snprintf((char*)serviceuuid, sizeof(UUID_Battery_Service), UUID_Battery_Service);
  ble_init_service_info(&battery, serviceuuid, 1, ENCRYPTED_MITM, ENCRYPTED_MITM, 100);

  ble_characteristic_params_t batteryvalor;
  batteryvalor.sccdIndex = 1;
  batteryvalor.cccdIndex = 2;
  uint8_t uuid[MAX_LENGTH_UUID];
  snprintf((char*)uuid, sizeof(UUID_Battery_Level_Characteristic), UUID_Battery_Level_Characteristic);
  ble_init_characteristic_params(&batteryvalor, 2, uuid, READABLE_BY_CLIENT_CH, 1, 0, 0, "64");


/* -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

  // Initialize Blood Pressure Service and its characteristics

  ble_service_info_t bloodpressure;
  snprintf((char*)serviceuuid, sizeof(UUID_Blood_Pressure_Service), UUID_Battery_Service);
  ble_init_service_info(&bloodpressure, (const uint8_t *)UUID_Blood_Pressure_Service, 3, OPEN, OPEN, 100);

  ble_characteristic_params_t bodysensor;
  snprintf((char*)uuid, sizeof(UUID_Body_Sensor_Location_Characteristic), UUID_Body_Sensor_Location_Characteristic);
  ble_init_characteristic_params(&bodysensor, 4, uuid, WRITABLE_WITH_ACK_CH + READABLE_BY_CLIENT_CH, 2, 0, 0, "40");

  ble_characteristic_params_t pressure;
  snprintf((char*)uuid, sizeof(UUID_Blood_Pressure_Measurement_Characteristic), UUID_Blood_Pressure_Measurement_Characteristic);
  ble_init_characteristic_params(&pressure, 5, uuid, READABLE_BY_CLIENT_CH, 3, 0, 0, "50");


 /* -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
  //Initialize custom service and its characteristics

  ble_service_info_t custom_service;
  snprintf((char*)serviceuuid, sizeof(testc), testc);
  ble_init_service_info(&custom_service, (const uint8_t *)testc, 3, NO_ACCESS, NO_ACCESS, 100);

  ble_characteristic_params_t custom_characteristic;
  snprintf((char*)uuid, sizeof(testc), testc);
  ble_init_characteristic_params(&custom_characteristic, 3, uuid, INDICATABLE_CH + READABLE_BY_CLIENT_CH, 5, 0, 0, "80");

  ble_init(&server);
  ble_default_parameters();
  ble_io_capability_parameter(DISPLAY_ONLY);
  ble_save();
  HAL_Delay(8000);
  ble_set_passkey(123456);

  // Start the services

  ble_create_service(&battery, &server);
  ble_start_characteristic(&batteryvalor, &battery);
  ble_end_service(&battery, &server);

  ble_create_service(&bloodpressure, &server);
  ble_start_characteristic(&bodysensor, &bloodpressure);
  ble_start_characteristic(&pressure, &bloodpressure);
  ble_end_service(&bloodpressure, &server);

  ble_create_service(&custom_service, &server);
  ble_start_characteristic(&custom_characteristic, &custom_service);
  ble_end_service(&custom_service, &server);

  ConnectionInf newconnection;
  uint8_t buffer[1000];

  /* Infinite loop */
  while(1)
  {
	 ble_listening(newconnection, &server);
	 while(1)
	 {
		 HAL_Delay(1000);

		 ble_change_value_characteristic(&batteryvalor, "50", READABLE_BY_CLIENT_CH);
		 ble_change_value_characteristic(&custom_characteristic, "123", INDICATABLE_CH);

		 if(reader(&server, &buffer) == STATUS_DISCONNECTED)
		 {
			 break;
		 }
	 }
  }
}
/**
  * @brief System Clock Configuration
  * @retval None
  */
void SystemClock_Config(void)
{
  RCC_OscInitTypeDef RCC_OscInitStruct = {0};
  RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};

  /** Configure the main internal regulator output voltage
  */
  if (HAL_PWREx_ControlVoltageScaling(PWR_REGULATOR_VOLTAGE_SCALE4) != HAL_OK)
  {
    Error_Handler();
  }

  /** Initializes the CPU, AHB and APB buses clocks
  */
  RCC_OscInitStruct.OscillatorType = RCC_OSCILLATORTYPE_MSI;
  RCC_OscInitStruct.MSIState = RCC_MSI_ON;
  RCC_OscInitStruct.MSICalibrationValue = RCC_MSICALIBRATION_DEFAULT;
  RCC_OscInitStruct.MSIClockRange = RCC_MSIRANGE_4;
  RCC_OscInitStruct.PLL.PLLState = RCC_PLL_NONE;
  if (HAL_RCC_OscConfig(&RCC_OscInitStruct) != HAL_OK)
  {
    Error_Handler();
  }

  /** Initializes the CPU, AHB and APB buses clocks
  */
  RCC_ClkInitStruct.ClockType = RCC_CLOCKTYPE_HCLK|RCC_CLOCKTYPE_SYSCLK
                              |RCC_CLOCKTYPE_PCLK1|RCC_CLOCKTYPE_PCLK2
                              |RCC_CLOCKTYPE_PCLK3;
  RCC_ClkInitStruct.SYSCLKSource = RCC_SYSCLKSOURCE_MSI;
  RCC_ClkInitStruct.AHBCLKDivider = RCC_SYSCLK_DIV1;
  RCC_ClkInitStruct.APB1CLKDivider = RCC_HCLK_DIV1;
  RCC_ClkInitStruct.APB2CLKDivider = RCC_HCLK_DIV1;
  RCC_ClkInitStruct.APB3CLKDivider = RCC_HCLK_DIV1;

  if (HAL_RCC_ClockConfig(&RCC_ClkInitStruct, FLASH_LATENCY_0) != HAL_OK)
  {
    Error_Handler();
  }
}

/**
  * @brief USART1 Initialization Function
  * @param None
  * @retval None
  */
static void MX_USART1_UART_Init(void)
{

  /* USER CODE BEGIN USART1_Init 0 */

  /* USER CODE END USART1_Init 0 */

  /* USER CODE BEGIN USART1_Init 1 */

  /* USER CODE END USART1_Init 1 */
  huart1.Instance = USART1;
  huart1.Init.BaudRate = 115200;
  huart1.Init.WordLength = UART_WORDLENGTH_8B;
  huart1.Init.StopBits = UART_STOPBITS_1;
  huart1.Init.Parity = UART_PARITY_NONE;
  huart1.Init.Mode = UART_MODE_TX_RX;
  huart1.Init.HwFlowCtl = UART_HWCONTROL_RTS_CTS;
  huart1.Init.OverSampling = UART_OVERSAMPLING_16;
  huart1.Init.OneBitSampling = UART_ONE_BIT_SAMPLE_DISABLE;
  huart1.Init.ClockPrescaler = UART_PRESCALER_DIV1;
  huart1.AdvancedInit.AdvFeatureInit = UART_ADVFEATURE_NO_INIT;
  if (HAL_UART_Init(&huart1) != HAL_OK)
  {
    Error_Handler();
  }
  if (HAL_UARTEx_SetTxFifoThreshold(&huart1, UART_TXFIFO_THRESHOLD_1_8) != HAL_OK)
  {
    Error_Handler();
  }
  if (HAL_UARTEx_SetRxFifoThreshold(&huart1, UART_RXFIFO_THRESHOLD_1_8) != HAL_OK)
  {
    Error_Handler();
  }
  if (HAL_UARTEx_DisableFifoMode(&huart1) != HAL_OK)
  {
    Error_Handler();
  }
  /* USER CODE BEGIN USART1_Init 2 */

  /* USER CODE END USART1_Init 2 */

}

/**
  * @brief GPIO Initialization Function
  * @param None
  * @retval None
  */
static void MX_GPIO_Init(void)
{
/* USER CODE BEGIN MX_GPIO_Init_1 */
/* USER CODE END MX_GPIO_Init_1 */

  /* GPIO Ports Clock Enable */
  __HAL_RCC_GPIOA_CLK_ENABLE();

/* USER CODE BEGIN MX_GPIO_Init_2 */
/* USER CODE END MX_GPIO_Init_2 */
}

/* USER CODE BEGIN 4 */

/* USER CODE END 4 */

/**
  * @brief  This function is executed in case of error occurrence.
  * @retval None
  */
void Error_Handler(void)
{
  /* USER CODE BEGIN Error_Handler_Debug */
  /* User can add his own implementation to report the HAL error return state */
  __disable_irq();
  while (1)
  {
  }
  /* USER CODE END Error_Handler_Debug */
}

#ifdef  USE_FULL_ASSERT
/**
  * @brief  Reports the name of the source file and the source line number
  *         where the assert_param error has occurred.
  * @param  file: pointer to the source file name
  * @param  line: assert_param error line source number
  * @retval None
  */
void assert_failed(uint8_t *file, uint32_t line)
{
  /* USER CODE BEGIN 6 */
  /* User can add his own implementation to report the file name and line number,
     ex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */
  /* USER CODE END 6 */
}
#endif /* USE_FULL_ASSERT */
