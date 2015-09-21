#! /usr/bin/python


from org.lz1aq.rig_interfaces import I_Radio


class Icom(I_Radio):
    """
    Configuration script for Icom transcievers
    """
    
    
    def encode_SetFreq(self, freq, vfo):
        """
        Get the command for frequency change
        
        :return: String of bytes ready to be send to the Rig
        """
        return "set freq command: "+freq.__str__()+ "  ;for VFO: "+vfo.__str__()
    