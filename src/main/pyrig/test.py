import json
from utils import getListInHex


TRANS_START = bytearray([0xFE, 0xFE, 0xE0])


jsonCommandContent = dict()

jsonCommandContent["frequency"] = "14195000"
jsonCommandContent["vfo"] = getListInHex(TRANS_START)

jsonBlock = dict()
jsonBlock["frequency"] = jsonCommandContent
print json.dumps(jsonBlock, indent=4)