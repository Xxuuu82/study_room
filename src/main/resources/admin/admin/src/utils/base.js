const base = {
    get() {
        return {
            url : "http://localhost:8082/study_room/",
            name: "study_room",
            // 退出到首页链接
            indexUrl: 'http://localhost:8082/study_room/front/dist/index.html#/index/home'
        };
    },
    getProjectName(){
        return {
            projectName: "智慧自习室预约系统 "
        } 
    }
}
export default base
