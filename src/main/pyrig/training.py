import threading
from time import sleep




class Game:

    _lock = threading._RLock()
    mark = 5
    start = 0

    @classmethod
    def plus(self):
        while(1):

            with Game._lock:
                if self.start == 0:
                    pass
                if self.mark==9 or self.mark==1:
                    break
                self.mark +=1
                self.prints(self.mark)
                sleep(0.5)

    @classmethod
    def minus(self):
         while(1):

            with Game._lock:
                if self.start == 0:
                    pass
                if self.mark==9 or self.mark==1:
                    break
                self.mark -=1
                self.prints(self.mark)
                sleep(0.5)

    @staticmethod
    def prints(position):
        msg = ""
        msg += "|"
        for i in range(1,10):
            if(i==position):
                msg += "* "
            else:
                msg += "_ "

        msg += "|"
        print msg


if __name__ == "__main__":

    thread1 = threading.Thread(target = Game.plus )
    thread2 = threading.Thread(target = Game.minus)

    thread1.start()
    thread2.start()

    Game.start = 1
    print "thread finished...exiting"