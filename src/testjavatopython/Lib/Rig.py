#! /usr/bin/python


from testjavatopython import I_Rig


class Rig(I_Rig):
    """
    A rig is an equipment which can be controlled with the help of commands
    send from the program to the rig. The Rig can also send data backwards.
    """
    
    @property
    def getManufacturer(self):
        """
        Returns a string specifying the manufacturer of the rig - E.g. "Kenwood"
        """
        raise NotImplementedError("getManufacturer")
    
    
    @property
    def getModel(self):
        """
        Returns a string specifying the model of the Rig - E.g. "IC-756PRO"
        """
        raise NotImplementedError("getModel")
    
    
    @property
    def getSerialPortSettings(self):
        """
        Returns a JSON formatted string describing the COM port settings that
        needs to be used when communicating with this Rig.
        """
        raise NotImplementedError("getSerialPortSettings")
    
    
    @property
    def decode(self, stringOfBytes):
        """
        Decodes information coming from the Rig.
        Converts string of bytes comming from the rig into a JSON formatted
        command.

        :return: json formatted command.
        """
        raise NotImplementedError("decode")
    
    
    @property
    def encode_Init(self):
        """
        If a rig needs some initialization before being able to be used.
        
        :return: Initialization command that is to be send to the Rig
        """
        raise NotImplementedError("encode_Init")
    
    
    @property
    def encode_Cleanup(self):
        """
        If a rig needs some cleanup after being used.
        
        :return: Cleanup command that is to be send to the Rig 
        """
        raise NotImplementedError("encode_Cleanup")
    