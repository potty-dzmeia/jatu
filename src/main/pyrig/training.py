

class Board(object):

    def __init__(self):
        self._rows = list()

        for x in range(0,8):
            self._rows.append([None, None, None, None, None, None, None, None])

    def reset(self):
        for x in range(0,8,2):
            (self._rows[0])[x] = Piece.createBlackMan()
        for x in range(1,9,2):
            (self._rows[1])[x] = Piece.createBlackMan()

    def setPiece(self, piece, x, y):
        if (self._rows[x])[y] != None:
            raise Exception("Square is not free")

        self._rows[x][y] = piece

    def __repr__(self):
        msg = ""
        for i in self._rows:
            msg += i.__str__() + "\n"
            # msg += str(r) + " " + str(value) + "\n"

        return msg

class Piece(object):

    def __init__(self, color="white", type="man"):
        self._color = color
        self._type = type

    def __repr__(self):
        if self._color == "white":
            if self._type == "man":
                return "w".ljust(4)
            else:
                return "W".ljust(4)
        else:
            if self._type == "man":
                return "b".ljust(4)
            else:
                return "B".ljust(4)

    @classmethod
    def createWhiteMan(cls):
        return Piece("white", "man")

    @classmethod
    def createWhiteKing(cls):
        return Piece("white", "king")

    @classmethod
    def createBlackMan(cls):
        return Piece("black", "man")

    @classmethod
    def createBlackKing(cls):
        return Piece("black", "king")

b = Board()
print b