import ElementPlus from 'element-plus';
import { createI18n } from 'vue-i18n/dist/vue-i18n.esm-bundler.js';
import 'element-plus/lib/theme-chalk/index.css';
import localeZH from 'element-plus/lib/locale/lang/zh-cn';
import localeEN from 'element-plus/lib/locale/lang/en';
import messages from '../utils/i18n';

// 国际化
const i18n = createI18n({
  locale: localeZH.name,
  fallbackLocale: localeEN.name,
  messages,
})

export default (app) => {
  app.use(ElementPlus, { locale:localeZH })
  app.use(i18n)
}
