#! /usr/bin/python


from testjavatopython import RigType

class Rig(RigType):
    """
    A rig is an equipment which can be controlled with the help of commands
    send from the program to the rig.
    The Rig can also send info to the Radio.
    """
    
    def __init__(self, id):
        self.idd = id;
        pass
        
    def getManufacturer(self):
        """
        Returns a strin g specifying the manufacturer of the rig - E.g. "Kenwood"
        """
        return "RigManufacturer"
    
    def getModel(self):
        """
        Returns a string specifying the model of the radio - E.g. "IC-756PRO"
        """
        return "RigModel"
    
    
    def getSerialPortSettings(self):
        """
        Returns a JSON formatted string describing the COM port settings that
        needs to be used when communicating with this Rig.
        """
        return "RigSerialPortSettings"
    
    
    #def open(self):
    #    return 0;
    
    #def close(self):
    #    return 0;
    
    def encodeSetFreq(self, freq, vfo):
        """
        Builds a command for frequency change of the RIG which is ready to be
        send.
        """
        return "encodeSetFreq"
    
    
   #def encode(self, jsonCommand):
        """
        Converts a command (e.g. set Frequency to 14,312,323) into symbols that
        are to be send to the radio.

        :param jsonCommand: JSON formatted command which will be translated to
                            string of bytes understandable by the given radio

        :return: String of bytes ready to be send to the radio
        """
     #   return 0
   
   
   
   
    #def decode(self, stringOfBytes):
        """
        Decodes information comming from the radio.
        Converts string of bytes comming from the radio into a JSON formatted
        command.

        :param stringOfBytes: JSON formatted command which will be translated to
                            string of bytes understandable by the given radio

        :return: json formatted command.
        """
     #   return 0
    
    #init
    #open
    #close
    #cleanup
       
    #Freq - frequency of the target VFO
    #rig_set_mode(RIG *rig, vfo_t vfo, rmode_t mode, pbwidth_t width)
    #rig_set_vfo(RIG *rig, vfo_t vfo) - set the current VFO
    #rig_get_ant(RIG *rig, vfo_t vfo, ant_t *ant) - get the current antenna
    #rig_set_powerstat(RIG *rig, powerstat_t status) - turn on/off the radio
    #rig_reset(RIG *rig, reset_t reset) - reset the radio
    #rig_send_morse(RIG *rig, vfo_t vfo, const char *msg) - send morse code
    #rig_get_info(RIG *rig) -  get general information from the radio
    #set_power(watts) - 
    