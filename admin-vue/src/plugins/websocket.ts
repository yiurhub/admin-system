// 客户端
export let websocket : WebSocket;

// 创建客户端
export const createWebSocket = (url: string) => {
    websocket = new WebSocket(`ws://localhost:9786/websocket/${url}`);
}
