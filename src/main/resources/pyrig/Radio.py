#! /usr/bin/python


from org.lz1aq.rig_interfaces import I_Radio


class Radio(I_Radio):
        
    @property    
    def encode_SetFreq(self, freq, vfo):
        """
        Get the command for frequency change
        
        :return: String of bytes ready to be send to the Rig
        """
        raise NotImplementedError("encode_SetFreq")
    
    
     
    
       
    #Freq - frequency of the target VFO
    #rig_set_mode(RIG *rig, vfo_t vfo, rmode_t mode, pbwidth_t width)
    #rig_set_vfo(RIG *rig, vfo_t vfo) - set the current VFO
    #rig_get_ant(RIG *rig, vfo_t vfo, ant_t *ant) - get the current antenna
    #rig_set_powerstat(RIG *rig, powerstat_t status) - turn on/off the radio
    #rig_reset(RIG *rig, reset_t reset) - reset the radio
    #rig_send_morse(RIG *rig, vfo_t vfo, const char *msg) - send morse code
    #rig_get_info(RIG *rig) -  get general information from the radio
    #set_power(watts) - 
    