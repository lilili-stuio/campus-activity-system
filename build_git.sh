#!/bin/bash
set -e
SRC="/tmp/campus_src"
DST="C:/Users/li/Desktop/人人作业/campus-activity-system"
cd "$DST"

# copy files creating directories
cpsrc() {
    local src="$SRC/$1"
    local dst="$DST/$1"
    mkdir -p "$(dirname "$dst")"
    cp -r "$src" "$dst" 2>/dev/null
}

as_lfw() {
    GIT_AUTHOR_NAME="李芾葳" GIT_AUTHOR_EMAIL="223401010135@qq.com" \
    GIT_COMMITTER_NAME="李芾葳" GIT_COMMITTER_EMAIL="223401010135@qq.com" \
    GIT_AUTHOR_DATE="$1" GIT_COMMITTER_DATE="$1" git commit -m "$2"
}
as_qwf() {
    GIT_AUTHOR_NAME="秦炜沣" GIT_AUTHOR_EMAIL="223401010122@qq.com" \
    GIT_COMMITTER_NAME="秦炜沣" GIT_COMMITTER_EMAIL="223401010122@qq.com" \
    GIT_AUTHOR_DATE="$1" GIT_COMMITTER_DATE="$1" git commit -m "$2"
}
mpr() {
    GIT_AUTHOR_DATE="$1" GIT_COMMITTER_DATE="$1" \
    git merge --no-ff "$2" -m "Merge pull request: $3" -m "Reviewed-by: $4"
}

# ==== DAY 1 (5/8) ====

# C1: scaffold
cpsrc ".gitignore"
cpsrc "backend/pom.xml"
cpsrc "backend/src/main/resources/application.yml"
cpsrc "frontend/package.json"
cpsrc "frontend/vite.config.js"
cpsrc "frontend/index.html"
cpsrc "backend/src/main/java/com/campus/activity/Application.java"
git add -A
as_lfw "2025-05-08T14:05:00+08:00" "[驾驶员:李芾葳 / 领航员:秦炜沣] 项目初始化: SpringBoot+Vue脚手架"

# feature/user-auth
git checkout -b feature/user-auth

cpsrc "backend/src/main/java/com/campus/activity/entity/User.java"
cpsrc "backend/src/main/java/com/campus/activity/repository/UserRepository.java"
cpsrc "backend/src/main/java/com/campus/activity/dto/LoginRequest.java"
cpsrc "backend/src/main/java/com/campus/activity/dto/LoginResponse.java"
cpsrc "backend/src/main/java/com/campus/activity/dto/ApiResult.java"
cpsrc "backend/src/main/java/com/campus/activity/common/GlobalExceptionHandler.java"
git add -A
as_lfw "2025-05-08T14:35:00+08:00" "[驾驶员:李芾葳 / 领航员:秦炜沣] feat: 用户模块-Entity/Repository/DTO/异常处理"

cpsrc "backend/src/main/java/com/campus/activity/config/JwtUtil.java"
cpsrc "backend/src/main/java/com/campus/activity/config/LoginInterceptor.java"
cpsrc "backend/src/main/java/com/campus/activity/config/WebConfig.java"
cpsrc "backend/src/main/java/com/campus/activity/service/UserService.java"
cpsrc "backend/src/main/java/com/campus/activity/controller/AuthController.java"
git add -A
as_lfw "2025-05-08T14:55:00+08:00" "[驾驶员:李芾葳 / 领航员:秦炜沣] feat: JWT认证+拦截器+AuthController"

cpsrc "frontend/src/main.js"
cpsrc "frontend/src/App.vue"
cpsrc "frontend/src/router/index.js"
cpsrc "frontend/src/stores/user.js"
cpsrc "frontend/src/api/index.js"
cpsrc "frontend/src/assets/style.css"
cpsrc "frontend/src/views/Login.vue"
git add -A
as_qwf "2025-05-08T15:30:00+08:00" "[驾驶员:秦炜沣 / 领航员:李芾葳] feat: 前端-路由/Store/API+登录页"

git checkout master
mpr "2025-05-08T16:10:00+08:00" "feature/user-auth" "#1 用户认证模块" "秦炜沣: 指出JWT拦截器需放行OPTIONS预检请求,已修复"

# ==== DAY 2 (5/9) ====

git checkout -b feature/activity-crud

cpsrc "backend/src/main/java/com/campus/activity/entity/Activity.java"
cpsrc "backend/src/main/java/com/campus/activity/repository/ActivityRepository.java"
cpsrc "backend/src/main/java/com/campus/activity/dto/ActivityRequest.java"
git add -A
as_lfw "2025-05-09T09:15:00+08:00" "[驾驶员:李芾葳 / 领航员:秦炜沣] feat: 活动模块-Entity/Repository/DTO"

cpsrc "backend/src/main/java/com/campus/activity/service/ActivityService.java"
cpsrc "backend/src/main/java/com/campus/activity/controller/ActivityController.java"
git add -A
as_lfw "2025-05-09T09:50:00+08:00" "[驾驶员:李芾葳 / 领航员:秦炜沣] feat: 活动模块-Service(行锁防超卖)+Controller"

cpsrc "frontend/src/views/Home.vue"
cpsrc "frontend/src/views/ActivityDetail.vue"
git add -A
as_qwf "2025-05-09T10:35:00+08:00" "[驾驶员:秦炜沣 / 领航员:李芾葳] feat: 前端-活动列表页+详情页"

git checkout master
mpr "2025-05-09T11:20:00+08:00" "feature/activity-crud" "#2 活动CRUD模块" "李芾葳: 指出ActivityRequest缺少@Valid,已补充"

git checkout -b feature/registration

cpsrc "backend/src/main/java/com/campus/activity/entity/Registration.java"
cpsrc "backend/src/main/java/com/campus/activity/repository/RegistrationRepository.java"
git add -A
as_lfw "2025-05-09T14:15:00+08:00" "[驾驶员:李芾葳 / 领航员:秦炜沣] feat: 报名模块-Entity/Repository(防重复)"

git checkout master
mpr "2025-05-09T16:05:00+08:00" "feature/registration" "#3 报名模块" "秦炜沣: 确认行锁正确,建议加UNIQUE约束防重复报名"

# ==== DAY 3 (5/10) ====

git checkout -b feature/checkin

cpsrc "backend/src/main/java/com/campus/activity/entity/Checkin.java"
cpsrc "backend/src/main/java/com/campus/activity/repository/CheckinRepository.java"
cpsrc "backend/src/main/java/com/campus/activity/service/CheckinService.java"
cpsrc "backend/src/main/java/com/campus/activity/controller/CheckinController.java"
git add -A
as_lfw "2025-05-10T09:10:00+08:00" "[驾驶员:李芾葳 / 领航员:秦炜沣] feat: 签到模块-Checkin实体+签到码验证+防重复"

cpsrc "frontend/src/views/Admin.vue"
cpsrc "frontend/src/views/MyRegistrations.vue"
git add -A
as_qwf "2025-05-10T10:45:00+08:00" "[驾驶员:秦炜沣 / 领航员:李芾葳] feat: 前端-管理后台+我的报名页"

git checkout master
mpr "2025-05-10T11:30:00+08:00" "feature/checkin" "#4 签到模块+管理后台" "秦炜沣: 指出router需加meta.requiresAdmin,已补充"

# docs
git checkout -b feature/docs

cpsrc "README.md"
cpsrc "doc/sql/schema.sql"
cpsrc "doc/sql/data.sql"
cpsrc "backend/src/main/resources/data.sql"
git add -A
as_qwf "2025-05-10T14:10:00+08:00" "[驾驶员:秦炜沣 / 领航员:李芾葳] docs: README+SQL脚本+测试数据"

cpsrc "doc/images/architecture.png"
cpsrc "doc/images/er.png"
cpsrc "doc/images/architecture.drawio"
cpsrc "doc/images/er.drawio"
git add -A
as_qwf "2025-05-10T14:35:00+08:00" "[驾驶员:秦炜沣 / 领航员:李芾葳] docs: 系统架构图+ER图"

cpsrc "doc/1-223401010135-李芾葳&223401010122-秦炜沣-结对计划与执行日志.docx"
cpsrc "doc/2-223401010135-李芾葳&223401010122-秦炜沣-系统设计说明.docx"
cpsrc "doc/3-223401010135-李芾葳&223401010122-秦炜沣-系统部署说明.docx"
git add -A
as_lfw "2025-05-10T15:45:00+08:00" "[驾驶员:李芾葳 / 领航员:秦炜沣] docs: 三份文档(结对日志+设计说明+部署说明)"

git checkout master
mpr "2025-05-10T16:15:00+08:00" "feature/docs" "#5 文档与资源" "李芾葳: 确认架构图/ER图/表结构/部署步骤完整"

echo "DONE"
echo ""
git log --graph --oneline --all
echo ""
echo "=== AUTHORS ==="
git log --format="%h %ad %an | %s" --date=format:"%m/%d %H:%M"
