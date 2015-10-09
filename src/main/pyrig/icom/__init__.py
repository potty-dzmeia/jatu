__author__ = 'chavdar'

from icom import Icom


#Set default logging handler to avoid "No handler found" warnings.
import logging
try:  # Python 2.7+
    from logging import NullHandler
except ImportError:
    class NullHandler(logging.Handler):
        def emit(self, record):
            pass
#
# print ".dffdfdsfds............"
# logging.getLogger(__name__).addHandler(NullHandler())

# import logging
# from logging.config import dictConfig
#
#
# logging_config = dict(
#     version = 1,
#     formatters = {
#         'f': {'format':
#               '%(asctime)s %(name)-12s %(levelname)-8s %(message)s'}
#         },
#     handlers = {
#         'h': {'class': 'logging.StreamHandler',
#               'formatter': 'f',
#               'level': logging.DEBUG}
#         },
#     loggers = {
#         'root': {'handlers': ['h'],
#                  'level': logging.DEBUG}
#         }
# )
#
# logging.getLogger(__name__).addHandler(logging.StreamHandler())
