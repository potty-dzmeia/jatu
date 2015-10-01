import os
import sys



print sys.path
print os.path.dirname(os.path.realpath(__file__))

sys.path.append("/home/potty/development/projects/jatu/src/main/java")
print sys.path