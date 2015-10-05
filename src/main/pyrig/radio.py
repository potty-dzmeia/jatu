from org.lz1aq.pyrig_interfaces import I_Radio

# class Modes
#     CW = 1

class Radio(I_Radio):


    # modes =['none'  ,
    #         'am'    ,  # AM -- Amplitude Modulation
    #         'cw'    ,  # CW - CW "normal" sideband
    #         'usb'   ,  # USB - Upper Side Band
    #         'lsb'   ,  # LSB - Lower Side Band
    #         'rtty'  ,  # RTTY - Radio Teletype
    #         'fm'    ,  # FM - "narrow" band FM
    #         'wfm'   ,  # WFM - broadcast wide FM
    #         'cwr'   ,  # CWR - CW "reverse" sideband
    #         'rttyr' ,  # RTTYR - RTTY "reverse" sideband
    #         'ams'   , # AMS - Amplitude Modulation Synchronous
    #         'pktlsb', # PKTLSB - Packet/Digital LSB mode (dedicated port)
    #         'pktusb', # PKTUSB - Packet/Digital USB mode (dedicated port)
    #         'pktfm' , # PKTFM - Packet/Digital FM mode (dedicated port)
    #         'ecssusb', # ECSSUSB - Exalted Carrier Single Sideb and USB
    #         'ecsslsb', # ECSSLSB - Exalted Carrier Single Sideband LSB
    #         'fax'   , # FAX - Facsimile Mode
    #         'sam'   , # SAM - Synchronous AM double sideband
    #         'sal'   , # SAL - Synchronous AM lower sideband
    #         'sah'   , # SAH - Synchronous AM upper (higher) sideband
    #         'dsb'   , # DSB - Double sideband suppressed carrier
    # ]

    @classmethod
    def getManufacturer(cls):
        raise NotImplementedError("getManufacturer")


    @classmethod
    def getModel(cls):
        raise NotImplementedError("getModel")



    @classmethod
    def encodeSetFreq(cls, freq):
        """
        Gets the command with which we can tell the radio to change current frequency
        :param freq: Specifying the frequency. E.g. 7100000 for 7.1MHz
        :type freq: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        raise NotImplementedError("encode_SetFreq")


    @classmethod
    def encodeGetFreq(cls):
        """
        Gets the command with which we can tell the radio send us the current frequency
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        raise NotImplementedError("encode_SetFreq")


    @classmethod
    def encodeSetVfoFreq(cls, freq, vfo):
        """
        Gets the command with which we can tell the radio to change frequency

        :param freq: Specifying the frequency. E.g. 7100000 for 7.1MHz
        :type freq: int
        :param vfo: The vfo for which we want to set the frequency
        :type vfo: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        raise NotImplementedError("encode_SetFreq")


    @classmethod
    def encodeGetVfoFreq(cls, vfo):
        """
        Gets the command with which we can tell the radio send us the current frequency

        :param vfo: For which VFO we want the mode
        :type vfo: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        raise NotImplementedError("encode_SetFreq")


    @classmethod
    def encodeSetMode(cls, mode):
        """
        Get the command that must be send to the radio in order to set mode (e.g. CW)

        :param mode: Specifies the mode - see Radio.modes
        :type mode: str
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        raise NotImplementedError("encode_SetFreq")


    @classmethod
    def encodeGetMode(cls):
        """
        Gets the command with which we can tell the radio to send us the current mode

        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        raise NotImplementedError("encode_SetFreq")


    @classmethod
    def encodeSetVfoMode(cls, mode, vfo):
        """
        Get the command that must be send to the radio in order to set mode (e.g. CW)

        :param mode: Specifies the mode - see Radio.modes
        :type mode: str
        :param vfo: The vfo which mode must be changed
        :type vfo: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        raise NotImplementedError("encode_SetFreq")


    @classmethod
    def encodeGetVfoMode(cls, vfo):
        """
        Gets the command with which we can tell the radio to send us the current mode

        :param vfo: The VFO for which we want the current mode
        :type vfo: int
        :return: Object containing transaction with some additional control settings
        :rtype: EncodedTransaction
        """
        raise NotImplementedError("encode_SetFreq")


    @classmethod
    def getModes(cls):
        """
        The function returns a string with all the modes that it supports.
        Example: "cw ssb lsb"

        :return: A string with the supported modes. Each mode is separated from the next with space.
        :rtype: str
        """
        raise NotImplementedError("getModes")


    @classmethod
    def getBands(cls):
        """
        The function returns a string with all the bands that it supports.
        Example: "3.5 7 14"

        :return: A string with the supported bands. Each band is separated from the next with space.
        :rtype: str
        """
        raise NotImplementedError("getBands")



    #Freq - frequency of the target VFO
    #rig_set_mode(RIG *rig, vfo_t vfo, rmode_t mode, pbwidth_t width)
    #rig_set_vfo(RIG *rig, vfo_t vfo) - set the current VFO
    #rig_get_ant(RIG *rig, vfo_t vfo, ant_t *ant) - get the current antenna
    #rig_set_powerstat(RIG *rig, powerstat_t status) - turn on/off the radio
    #rig_reset(RIG *rig, reset_t reset) - reset the radio
    #rig_send_morse(RIG *rig, vfo_t vfo, const char *msg) - send morse code
    #rig_get_info(RIG *rig) -  get general information from the radio
    #set_power(watts) -


      # modes ={'NONE':     0,
    #         'AM':       1,  # AM -- Amplitude Modulation
    #         'CW':       2,  # CW - CW "normal" sideband
    #         'USB':      3,  # USB - Upper Side Band
    #         'LSB':      4,  # LSB - Lower Side Band
    #         'RTTY':     5,  # RTTY - Radio Teletype
    #         'FM':       6,  # FM - "narrow" band FM
    #         'WFM':      7,  # WFM - broadcast wide FM
    #         'CWR':      8,  # CWR - CW "reverse" sideband
    #         'RTTYR':    9,  # RTTYR - RTTY "reverse" sideband
    #         'AMS':      10, # AMS - Amplitude Modulation Synchronous
    #         'PKTLSB':   11, # PKTLSB - Packet/Digital LSB mode (dedicated port)
    #         'PKTUSB':   12, # PKTUSB - Packet/Digital USB mode (dedicated port)
    #         'PKTFM':    13, # PKTFM - Packet/Digital FM mode (dedicated port)
    #         'ECSSUSB':  14, # ECSSUSB - Exalted Carrier Single Sideband USB
    #         'ECSSLSB':  15, # ECSSLSB - Exalted Carrier Single Sideband LSB
    #         'FAX':      16, # FAX - Facsimile Mode
    #         'SAM':      17, # SAM - Synchronous AM double sideband
    #         'SAL':      18, # SAL - Synchronous AM lower sideband
    #         'SAH':      19, # SAH - Synchronous AM upper (higher) sideband
    #         'DSB':      20, # DSB - Double sideband suppressed carrier
    # }
    