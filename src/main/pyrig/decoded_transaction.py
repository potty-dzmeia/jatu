from org.lz1aq.pyrig_interfaces import I_Rig

class DecodedTransaction(I_Rig.I_DecodedTransaction):
    """
    Contains the decoded transaction coming from the rig together
    with some additional control information
    """

    def __init__(self, transaction, bytes_read):
        """
        :param transaction: [string] JSON formatted string with the decoded transaction
        :param bytes_read: [int] the amount of bytes that were read from the supplied buffer in order to decode the transaction
        :return:
        """
        self.transaction_ = transaction
        self.bytes_read_ = bytes_read


    def getTransaction(self):
        """
        :return: [string] JSON formatted string with the decoded transaction
        """
        return self.transaction_


    def getBytesRead(self):
        """
        :return: [int] the amount of bytes that were read from the supplied buffer in order to decode the transaction
        """
        return self.bytes_read_

