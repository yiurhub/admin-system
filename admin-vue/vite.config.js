import vue from '@vitejs/plugin-vue'
import {defineConfig} from "vite";

export default ({ mode }) => {
    return defineConfig({
        base: './',
        plugins: [vue()],
        optimizeDeps: {
            include: ['schart.js']
        },
        server: {
            port: 8080,
            proxy: {
                '/api': {
                    target: 'http://localhost:9786/',
                    changeOrigin: true,
                    rewrite: path => path.replace(/^\/api/, '')
                }
            }
        }
    })
}