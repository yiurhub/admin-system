import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/icon.css'
import element from './plugins/element'
// jsonViewer
import JsonViewer from "vue3-json-viewer"
import 'vue3-json-viewer/dist/index.css'
// markdown editor
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
// markdown preview
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
// markdown theme
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
// prismjs
import Prism from 'prismjs';
// highlightjs
import hljs from 'highlight.js';

VueMarkdownEditor.use(vuepressTheme, {
    Prism
});
VMdPreview.use(githubTheme, {
    Hljs: hljs
});

const app = createApp(App)
element(app)
app
    .use(store)
    .use(router)
    .use(JsonViewer)
    .use(VueMarkdownEditor)
    .use(VMdPreview)
    .mount('#app')
