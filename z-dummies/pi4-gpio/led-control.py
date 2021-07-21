"""

Refer to:

https://gpiozero.readthedocs.io/en/stable/

"""

from gpiozero import LED

# Pin-1,17: 3.3V Power
# Pin-2,4: 5V Power
# Pin-6,9,14,20,25,30,34,39: Ground
# Pin-8: UART0 TX
# Pin-10: UART0 RX
# Pin-27,28: Reserved

gpio2 = LED(2) # Pin-3 I2C1 SDA
gpio3 = LED(3) # Pin-5 I2C1 SCL
gpio4 = LED(4) # Pin-7
gpio5 = LED(5) # Pin-29
gpio6 = LED(6) # Pin-31
gpio7 = LED(7) # Pin-26 SPI0 CS1
gpio8 = LED(8) # Pin-24 SPI0 CS0
gpio9 = LED(9) # Pin-21 SPI0 MISO 
gpio10 = LED(10) # Pin-19 SPI0 MOSI
gpio11 = LED(11) # Pin-23 SPI0 SCLK 
gpio12 = LED(12) # Pin-32
gpio13 = LED(13) # Pin-33
gpio16 = LED(16) # Pin-36 SPI1 CS0
gpio17 = LED(17) # Pin-11
gpio18 = LED(18) # Pin-12
gpio19 = LED(19) # Pin-35 SPI1 MISO
gpio20 = LED(20) # Pin-38 SPI1 MOSI
gpio21 = LED(21) # Pin-40 SPI1 SCLK
gpio22 = LED(22) # Pin-15
gpio23 = LED(23) # Pin-16
gpio24 = LED(24) # Pin-18
gpio25 = LED(25) # Pin-22
gpio26 = LED(26) # Pin-37
gpio27 = LED(27) # Pin-13

gpio19.on()
gpio19.off()
gpio19.blink(1, 1)
