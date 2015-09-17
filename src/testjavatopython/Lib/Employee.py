#! /usr/bin/python

# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

from testjavatopython import EmployeeType

class Employee(EmployeeType):
   def __init__(self, first, last, id):
      self.first = first
      self.last  =  last
      self.id = id

   def getEmployeeFirst(self):
      return self.first

   def getEmployeeLast(self):
      return self.last

   def getEmployeeId(self):
      return self.id
