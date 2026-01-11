#!/usr/bin/env python3
from http.server import HTTPServer, SimpleHTTPRequestHandler
import json
import os

SPRITES_DIR = os.path.join(os.path.dirname(__file__), 'sprites')

class SpriteHandler(SimpleHTTPRequestHandler):
    def do_POST(self):
        if self.path == '/save':
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            data = json.loads(post_data.decode('utf-8'))

            name = data.get('name', 'sprite')
            left = data.get('left', [])
            right = data.get('right', [])

            os.makedirs(SPRITES_DIR, exist_ok=True)

            filepath = os.path.join(SPRITES_DIR, f'{name}.json')
            with open(filepath, 'w') as f:
                json.dump({'left': left, 'right': right}, f, indent=2)

            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            self.wfile.write(json.dumps({'status': 'saved', 'path': filepath}).encode())
        else:
            self.send_response(404)
            self.end_headers()

    def do_GET(self):
        if self.path == '/list':
            files = [f.replace('.json', '') for f in os.listdir(SPRITES_DIR) if f.endswith('.json')]
            self.send_response(200)
            self.send_header('Content-type', 'application/json')
            self.end_headers()
            self.wfile.write(json.dumps(files).encode())
        elif self.path.startswith('/load/'):
            name = self.path[6:]
            filepath = os.path.join(SPRITES_DIR, f'{name}.json')
            if os.path.exists(filepath):
                with open(filepath) as f:
                    data = json.load(f)
                self.send_response(200)
                self.send_header('Content-type', 'application/json')
                self.end_headers()
                self.wfile.write(json.dumps(data).encode())
            else:
                self.send_response(404)
                self.end_headers()
        else:
            super().do_GET()

if __name__ == '__main__':
    os.chdir(os.path.dirname(__file__))
    port = 8080
    print(f'Sprite Editor running at http://localhost:{port}')
    HTTPServer(('0.0.0.0', port), SpriteHandler).serve_forever()
