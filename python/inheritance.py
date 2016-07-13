class Foo(object):
    def __init__(self, p1, p2):
        self.p1 = p1
        self.p2 = p2

    def my_method(self):
        print(self.p1)


class SubFoo(Foo):
    def __init__(self, p1, p2, p3):
        super(SubFoo, self).__init__(p1, p2)
        self.p3 = p3

    def my_method(self):
        super(SubFoo, self).my_method()
        print(self.p2)


foo = SubFoo(1, 2, 3)
foo.my_method()
a = 1
