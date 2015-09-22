#! /usr/bin/python


from org.lz1aq.rig_interfaces import I_AntennaTuner

class AntennaTuner(I_AntennaTuner):

    
    def getManufacturer(self):
        """
        Returns a string specifying the manufacturer of the rig - E.g. "Kenwood"
        """
        return "lz1aq"
    
    
    def getModel(self):
        """
        Returns a string specifying the model of the Rig - E.g. "IC-756PRO"
        """
        return "Automatic Antenna Tuner"

    def getSerialPortSettings(self):
        """
        Returns a JSON formatted string describing the COM port settings that
        needs to be used when communicating with this Rig.
        """
        return "afasdfasdf"
        