

#
# mode_codes = {'lsb': 0x00,
#               'usb': 0x01,
#               'am': 0x02,
#               'cw': 0x03,
#               'rtty': 0x04,
#               'fm': 0x05,
#               'cwr': 0x07,
#               'rttyr': 0x08}
#
#
# print " ".join("%s" % key for key in mode_codes)

class a(object):
    @classmethod
    def methodA(cls):
        return "class a; method A"

    @classmethod
    def methodB(cls):
        return "class a; method B"


class b(a):
    @classmethod
    def methodB(cls):
        return "class b; method B"


print b.methodA()