from org.lz1aq.pyrig_interfaces import I_Rig



class Rig(I_Rig):
    """
    A rig is an equipment which can be controlled with the help of commands
    send from the program to the rig. The Rig can also send data backwards.
    """

    
    
    @property
    def getManufacturer(self):
        """
        Returns the manufacturer of the rig - E.g. "Kenwood"

        :return: [String]
        """
        raise NotImplementedError("getManufacturer")


    @property
    def getModel(self):
        """
        Returns the model of the Rig - E.g. "IC-756PRO"

        :return: [String]
        """
        raise NotImplementedError("getModel")


    @property
    def getSerialPortSettings(self):
        """
        Returns the COM port settings that needs to be used when communicating with this Rig.

        :return: [SerialSettings] object
        """
        raise NotImplementedError("getSerialPortSettings")


    @property
    def decode(self, stringOfBytes):
        """
        Decodes information coming from the Rig.
        Converts string of bytes coming from the rig into a JSON formatted command.

        :param stringOfBytes: [string] Series of bytes from which we must extract the transaction. There is no guarantee
        that the first byte is the beginning of the transaction (i.e. there might be some trash in the beginning).
        :return: [DecodedTransaction] object containing the transaction and some additional control information
        """
        raise NotImplementedError("decode")


    @property
    def encodeInit(self):
        """
        If a rig needs some initialization before being able to be used.
        
        :return: [SerialTransaction] Initialization command that is to be send to the Rig
        """
        raise NotImplementedError("encode_Init")


    @property
    def encodeCleanup(self):
        """
        If a rig needs some cleanup after being used.
        
        :return: [SerialTransaction] Cleanup command that is to be send to the Rig
        """
        raise NotImplementedError("encode_Cleanup")
    