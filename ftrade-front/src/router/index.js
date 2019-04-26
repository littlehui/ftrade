import _ from 'lodash'
import home from '../pages/home'

const registerRoute = (config) => {
  const route = []

  for (const topMenu of config) {
    const currSubMenus = _.cloneDeep(topMenu.subMenus)
    for (const secondMenu of currSubMenus) {
      if (secondMenu.url) {
        const meta = {}
        const breadcrumbs = []
        let title = secondMenu.name
        if (secondMenu.title) title = secondMenu.title
        breadcrumbs.push({title})
        meta.breadcrumbs = breadcrumbs
        route.push({
          name: secondMenu.name,
          path: topMenu.url + secondMenu.url,
          meta,
          component: () => import(`../pages/${secondMenu.pageLocation}`)
      })
      } else {
        for (const thirdMenu of secondMenu.subMenus) {
          const currComponent = () =>
          import
          (`../pages/${thirdMenu.pageLocation}`)
          const meta = {}
          let breadcrumbs = []
          let title = thirdMenu.name
          if (thirdMenu.activeMenu) meta.activeMenu = thirdMenu.activeMenu
          if (thirdMenu.breadcrumbs) breadcrumbs = thirdMenu.breadcrumbs
          if (thirdMenu.title) title = thirdMenu.title
          breadcrumbs.push({title})
          meta.breadcrumbs = breadcrumbs
          let obj = {
            name: thirdMenu.name,
            path: topMenu.url + thirdMenu.url,
            meta,
            component: currComponent
          }
          route.push(obj)
        }
      }
    }
  }
  return {route}
}
var navConfig = require('./nav.config.json')
const route = registerRoute(navConfig)
route.route = route.route.concat([
  {
    path: '/',
    name: 'home',
    meta: {
      withoutAuth: false
    },
    component: home
  }
])
export default route.route
