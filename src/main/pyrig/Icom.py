#! /usr/bin/python
from org.lz1aq.rig_interfaces import I_Radio



# Serial port settings used by this file. Where:
# write_delay       - Delay between each byte sent out, in mS
# post_write_delay  - Delay between each commands send out, in mS
# timeout           - Timeout, in mS
# retry             - Maximum number of retries if command fails, 0 to disable
serial_comm_port_settings = """{
 "baudrate_min": 300,   
 "baudrate_max" : 19200,
 "data_bits": 8,
 "stop_bits" :1,
 "parity": "none",
 "handshake": "none",
 "write_delay": 0,
 "post_write_delay": 0,
 "timeout": 200,
 "retry": 3
}""" 



class Icom(I_Radio):
    """
    Configuration script for Icom transcievers
    """
    
    def getSerialPortSettings(self):
        """
        Returns a JSON formatted string describing the COM port settings that
        needs to be used when communicating with this Rig.
        """
        return serial_comm_port_settings;
      
    def encode_SetFreq(self, freq, vfo):
        """
        Get the command for frequency change
        
        :return: String of bytes ready to be send to the Rig
        """
        return "set freq command: "+freq.__str__()+ "  ;for VFO: "+vfo.__str__()
    