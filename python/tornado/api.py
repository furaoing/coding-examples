# -*- coding: utf-8 -*-

import tornado.web


class ApiHandle(tornado.web.RequestHandler):

    def post(self):
        byte_data = self.request.body
        raw_data = byte_data.decode(encoding="utf-8")

        # TODO: Write API Processing Logic Here

        self.write("OK")

