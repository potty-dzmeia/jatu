
mode = "MD$1;"
setFreq = "FA00007000000;"


def a(sss):
    return sss

def b(sss):
    return sss+"b"

parsers = {"FA": a,       # VFO A frequency
           "FB": b,       # VFO B frequency
         }                 # Operating mode


fn = parsers["FB"]
print fn("111")



