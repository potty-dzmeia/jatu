#! /usr/bin/python


from org.lz1aq.pyrig_interfaces import I_AntennaTuner
from serial_settings import SerialSettings

class AntennaTuner(I_AntennaTuner):

    # Get default serial port settings
    serial_settings = SerialSettings()
    # If different value than the default one is need - uncomment and set to desired value
    # serial_settings.baudrate_min_ = 2400
    # serial_settings.baudrate_max_ = 19200
    # serial_settings.data_bits_ = 8
    # serial_settings.stop_bits_ = 1
    # serial_settings.handshake_ = "None"       # possible values 'None', 'XON_XOFF' and 'CTS_RTS'
    # serial_settings.parity_ = "None"          # possible values 'None', 'Even', 'Odd', 'Mark', 'Space'


    @classmethod
    def getSerialPortSettings(cls):
        """
        Returns the serial settings to be used when connecting to this rig

        :return: [SerialSettings] object holding the serial port settings
        :rtype: SerialSettings
        """
        return cls.serial_settings


    @classmethod
    def getManufacturer(self):
        """
        :return: The manufacturer of the rig - E.g. "Kenwood"
        :rtype: str
        """
        return "lz1aq"
    

    @classmethod
    def getModel(self):
        """
        :return: The model of the Rig - E.g. "IC-756PRO"
        :rtype: str
        """
        return "Automatic Antenna Tuner"

        