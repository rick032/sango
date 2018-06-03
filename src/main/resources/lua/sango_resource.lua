-- ========== Settings ================
thisVersion=20180603
Settings:setCompareDimension(true, 1280)
Settings:setScriptDimension(true, 720)
Settings:set("MinSimilarity", 0.9)
--Settings:snapSet("OutputCropImg ", true)--當var 是OutputCaptureImg 且設成 true, 整個螢幕截圖將儲存成"capture.png"
screenWidth = getAppUsableScreenSize():getX()
screenHeight = getAppUsableScreenSize():getY()
setDragDropTiming(200, 200)
setDragDropStepCount(500)
setImmersiveMode(true)
toast("screenWidth:" .. screenWidth .. " screenHeight:" .. screenHeight)
-- ==========  main program ===========
dialogReg = Region(118, 47, 485, 314)
beAttackedReg = Region(260, 30, 150, 30)
chooseTeam = Region(80, 50, 120, 300)
soldierPicReg = Region(98, 89, 174, 158)
centerLocation = Location(340, 179)
hitBanditCount = 0
collectionCount = 0
teamActions = {}
teamDirections = {}
resourceActions = {}
dir = scriptPath()
setImagePath(dir .. "image")
startDate = os.date("%Y%m%d")
dirName = dir .. "/" .. startDate .. "/"
os.execute("mkdir " .. dirName)
fileName = dirName .. startDate .. ".txt"
file = io.open(fileName, "a")
file:write("IMEI:" .. getIMEI())
file:write(",IMSI:" .. getIMSI())
file:write(",MacAddr:" .. getMacAddr())
file:write(",SIMSerial:" .. getSIMSerial())
file:write(",Device Id:" .. getDeviceID() .. "\n")
--params = { imei = getIMEI(), macAddr = getMacAddr(), device = getDeviceID() }
varResult = httpGet("https://weighty-site-140903.appspot.com/check?macAddr=" .. getMacAddr().."&version=" ..thisVersion .."&imei=" .. getIMEI() .. "&deviceId=" .. getDeviceID())
--varResult = httpGet("http://localhost:8080/check?macAddr=" .. getMacAddr().."&imei=" .. getIMEI() .. "&deviceId=" .. getDeviceID())
toast("認證:" .. varResult)
if varResult == "N" then
    scriptExit("非認證過的機器 macAddr=" .. getMacAddr() .. " deviceId=" .. getDeviceID() .. " IMEI=" .. getIMEI())
elseif varResult == "1" then
    scriptExit("啟動時間未到 macAddr=" .. getMacAddr() )
elseif varResult == "2" then
    scriptExit("認證已過期 macAddr=" .. getMacAddr() )
elseif varResult == "3" then
    scriptExit("認證未啟用 macAddr=" .. getMacAddr() )
end

local deviceId = { "農田", "伐木", "採石", "治煉" }
function doUncheckAll()
    local i = 0
    while (true) do
        i = i + 1
        if (not existsClick("checked.png")) then
            return -1
        end
    end
end

--將主城畫面置中
function castleBeCenter()
    toast("將主城畫面置中")

    if existsClick("castle.png") then
        wait(1)
        if exists("castle.png") then
        elseif existsClick("map.png") then
        else
            wait(2)
            existsClick("map.png")
        end
    elseif existsClick("map.png") then
    else
        if not existsClick(Pattern("castle.png"):similar(0.4)) then
            click(Location(645, 336))
        end
    end
    --print(find("castle.png"))
end

--split 分解指定的字符
function split(str, del)
    p, nrep = str:gsub("%s*" .. del .. "%s*", "")
    return { str:match((("%s*(.-)%s*" .. del .. "%s*"):rep(nrep) .. "(.*)")) }
end


--設定採集等級
function settingResourceLevel(ResourceLevel)
    existsClick("close.png")
    --castleBeCenter()
    toast("設定" .. ResourceLevel .. "級採集")
    if (not existsClick("near.png")) then
        click(Location(665, 16)) --662,12
    end
    existsClick("resource.png")
    --click(dialogReg:find("levelSort.png"))
    click("levelSort.png")
    if exists(Pattern("displayAllResourceChecked.png"):similar(0.95)) then
        click(Pattern("displayAllResourceChecked.png"):targetOffset(-40, 0))
    end

    local match2Level = find("display2.png")
    local matchWood = find("wood.png")

    swipe(match2Level, matchWood)

    toast("do Uncheck All")
    doUncheckAll()
    local resourse_similar = 0.9
    if CheckFarmland then
        local matchLevel = find(Pattern("farmland.png"):similar(resourse_similar))
        click(Region(matchLevel:getX() - 30, matchLevel:getY(), 10, 10))
    end
    if CheckWood then
        local matchLevel = find(Pattern("wood.png"):similar(resourse_similar))
        click(Region(matchLevel:getX() - 30, matchLevel:getY(), 10, 10))
    end
    if CheckStone then
        local matchLevel = find(Pattern("stone.png"):similar(0.7))
        click(Region(matchLevel:getX() - 30, matchLevel:getY(), 10, 10))
    end
    if CheckIron then
        local matchLevel = find(Pattern("iron.png"):similar(resourse_similar))
        click(Region(matchLevel:getX() - 30, matchLevel:getY(), 10, 10))
    end

    --勾選採集
    local b = split(ResourceLevel, ",")
    for i, level in ipairs(b) do
        if exists("display" .. level .. ".png") then
            toast("開始勾選" .. level .. "級採集")
            local matchLevel = find(Pattern("display" .. level .. ".png"):similar(resourse_similar))
            local matchReg = Region(matchLevel:getX() - 30, matchLevel:getY(), 10, 10)
            --matchReg:highlight(2)
            click(matchReg)
        end
    end
    --dialogReg:save("../".. startDate .. "/resource.png")
    click("close.png")
    existsClick("close.png")
end

--設定匪寇等級
function settingBandit(BanditLevel)
    castleBeCenter()
    toast("設定" .. BanditLevel .. " 寇匪")
    if (not existsClick("near.png")) then
        click(Location(665, 16)) --662,12
    end
    existsClick("bandit.png")
    --click(dialogReg:find("levelSort.png"))
    click("levelSort.png")
    if exists("displayAllBanditChecked.png") then
        click(Pattern("displayAllBanditChecked.png"):targetOffset(-40, 0))
    end

    local match4Level = find("display4.png")
    local matchElite = find("displayEliteBandit.png")

    swipe(match4Level, matchElite)

    toast("do Uncheck All")
    doUncheckAll()
    --勾選普通寇匪
    local matchdisplayNormalBandit = find("displayNormalBandit.png")
    local normalReg = Region(matchdisplayNormalBandit:getX() - 30, matchdisplayNormalBandit:getY(), 10, 10)
    normalReg:highlight(2)
    click(normalReg)
    local b = split(BanditLevel, ",")
    for i, level in ipairs(b) do
        if exists("display" .. level .. ".png") then
            toast("開始勾選" .. level .. "級寇匪")
            local matchLevel = find(Pattern("display" .. level .. ".png"):similar(0.9))
            local matchReg = Region(matchLevel:getX() - 30, matchLevel:getY(), 10, 10)
            matchReg:highlight(2)
            click(matchReg)
        end
    end
    click("close.png")
    existsClick("close.png")
end

local loopAttackCount = 0
swipeDistince = screenHeight / 2
swipeCount = 0
function loopAttack(teamNum, pointA, pointB)
    loopAttackCount = loopAttackCount + 1
    --print("147action:"..teamNum)
    --print(teamActions)
    local action = teamActions[teamNum]
    local near = exists("near.png")
    if near then
        click(near)
    else
        --click(Pattern("near.png"):similar(0.50))
        click(Location(665, 16)) -- 662,12,41,32
    end
    local found = false
    if action == 1 then
        existsClick("resource.png")
        local resource
        --dialogReg:highlight(4)
        --print(resourceActions[teamNum] )
        local resourceSimilar = 0.97
        if resourceActions[teamNum] == 1 then
            resource = Pattern("goToFarmland.png"):similar(resourceSimilar)
        elseif resourceActions[teamNum] == 2 then
            --相似度0.95
            resource = Pattern("goToWood.png"):similar(resourceSimilar)
        elseif resourceActions[teamNum] == 3 then
            --因伐木場的相似度是0.966
            resource = Pattern("goToStone.png"):similar(resourceSimilar)
        elseif resourceActions[teamNum] == 4 then
            resource = Pattern("goToIron.png"):similar(resourceSimilar)
        end
        --print(resource)
        --dialogReg:highlight(2)
        local goToResource = dialogReg:exists(resource)
        if goToResource == nil then
            swipe(Location(370, 300), Location(370, 100))
            goToResource = dialogReg:exists(resource)
        end
        if goToResource ~= nil then
            toast("similar:"..goToResource:getScore())
            --goToResource = dialogReg:find(resource)
            goToResource:highlight(3)
            --print(goToResource)
            local goTo = goToResource:exists("GoTo.png")
            if goTo ~= nil then
                click(goTo)
                if existsClick("collection.png") then
                    found = true
                end
            end
        end
        --if goToResource ~= nil then

        --end
    elseif action == 2 then
        existsClick("bandit.png")
        if onlyHorseThief then
            local goToHorseThief = dialogReg:exists(Pattern("horseThief.png"):similar(0.99))
            if goToHorseThief == nil then
                swipe(Location(370, 300), Location(370, 100))
                goToHorseThief = dialogReg:exists(Pattern("horseThief.png"):similar(0.99))
            end
            if goToHorseThief ~= nil then
                goToHorseThief:highlight(3)
                local goTo = goToHorseThief:exists("GoTo.png")
                if goTo ~= nil then
                    click(goTo)
                    if existsClick("attack.png") then
                        found = true
                        wait(1)
                    end
                end
            end
        else
            local ex = dialogReg:exists("GoTo.png")
            if ex == nil then
                swipe(Location(370, 300), Location(370, 100))
                ex = dialogReg:exists("GoTo.png")
            end
            if ex ~= nil then
                local goTo = dialogReg:exists("GoTo.png")
                if goTo ~= nil then
                    click(goTo)
                    if existsClick("attack.png") then
                        found = true
                        wait(1)
                    end
                end
            end
        end
    end
    if found then
        local useMarch = exists("use2.png")
        if useMarch then
            if autoUse then
                if existsClick("use2.png") then
                    toast("使用行軍令")
                    existsClick("attack.png")
                    file:write("使用行軍令\n")
                end
            else
                print("無軍令可使用，請勾選自動使用軍令。")
                file:write("無軍令可使用，請勾選自動使用軍令。\n")
                return -1
            end
        end
        local found = false
        --local i = 1
        --設定那一隊
        --while (i <= allTeams and not found) do
        --local team = Pattern("team" .. i .. ".png"):similar(0.96) -- chooseTeam:find("team" .. i .. ".png")
        local team = Pattern("team" .. teamNum .. ".png"):similar(0.96)
        if (existsClick(team)) then
            toast("team" .. teamNum)
            if teamActions[teamNum] == 2 and exists("Departure.png") then
                click("Departure.png")
                hitBanditCount = hitBanditCount + 1
            elseif teamActions[teamNum] == 1 and exists("collection2.png") then
                click("collection2.png")
                collectionCount = collectionCount + 1
            end
            existsClick("close.png")
            loopAttackCount = 0

            castleBeCenter()
            return 1
        end

        --  i = i + 1
        --end
        toast("無可用閒置隊伍")
        existsClick("close.png")
        return 2
    else
        existsClick("close.png")
        if (loopAttackCount > 10) then
            --換方向
            loopAttackCount = 0
            castleBeCenter()
            return 3
        end
        if autoUseFreeBattle then
            toast("檢查兵臨城下")
            checkBeAttacked()
        end
        if direction == 1 then
            local west = Region(pointA:getX() + swipeDistince, pointA:getY(), 10, 10)
            toast("往東尋找第" .. loopAttackCount .. "次")
            swipe(west, pointA)
        elseif direction == 2 then
            local east = Region(pointB:getX() - swipeDistince, pointB:getY(), 10, 10)
            toast("往西尋找第" .. loopAttackCount .. "次")
            swipe(east, pointB)
        elseif direction == 3 then
            toast("往北尋找第" .. loopAttackCount .. "次")
            swipe(pointA, pointB)
        elseif direction == 4 then
            toast("往南尋找第" .. loopAttackCount .. "次")
            swipe(pointB, pointA)
        elseif direction == 5 then
            toast("往東北尋找第" .. loopAttackCount .. "次")
            swipe(pointA, pointB)
        elseif direction == 6 then
            toast("往西南尋找第" .. loopAttackCount .. "次")
            swipe(pointB, pointA)
        elseif direction == 7 then
            toast("往西北尋找第" .. loopAttackCount .. "次")
            swipe(pointA, pointB)
        elseif direction == 8 then
            toast("往東南尋找第" .. loopAttackCount .. "次")
            swipe(pointB, pointA)
        end

        return loopAttack(teamNum, pointA, pointB)
    end
end

function findBattle(teamNum)
    --8個方向
    local east
    local west
    local north = Location(392, 120)
    local south = Location(392, 320)
    local northEast = Location(500, 120)
    local southWest = Location(60, 320)
    local northWest = Location(60, 120)
    local southEast = Location(400, 320)
    if exists("flag.png") then
        west = find("flag.png")
    else
        west = find("building.png")
    end
    if exists("activity.png") then
        east = find("activity.png")
    else
        east = find("mall.png")
    end

    toast("開始尋找" .. BanditLevel)
    local count = 1
    -- 起始方向
    direction = teamDirections[teamNum]
    while (count < 9) do
        if direction > 9 then
            direction = 1
        end
        if direction < 3 then
            result = loopAttack(teamNum, west, east)
        elseif direction < 5 then
            result = loopAttack(teamNum, north, south)
        elseif direction < 7 then
            result = loopAttack(teamNum, northEast, southWest)
        else
            result = loopAttack(teamNum, northWest, southEast)
        end
        if (result == 3) then
            direction = direction + 1
        else
            return result
        end
        count = count + 1
    end
    --can't find the Bandit
    return 4
end

--被打
function checkBeAttacked()
    local beAttPic = beAttackedReg:exists("attacked.png")
    if (beAttPic) then
        --local beAttacked = beAttackedReg:find("attacked.png")
        --beAttacked:highlight(3)
        click(beAttPic)
        endTime = "Time:" .. os.date("%H%M%S")
        print("被攻擊時間:" .. endTime)
        file:write("被攻擊時間:" .. endTime .. "\n")
        --print("executeTime:"..(endTime - startTime) / 60 .. " mins")
        local homeBeAtt = dialogReg:exists("homeBeAttacked.png")
        dialogReg:save("../" .. startDate .. "/beAtt" .. endTime .. ".png")
        if homeBeAtt ~= nil then
            print("主城被攻擊!!")
            file:write("主城被攻擊!!")
            existsClick("close.png")
            if (existsClick("addBen.png") or existsClick("addBen2.png")) then
                --主城被打
                local matchDiv = find(Pattern("freeBattleDiv.png"):similar(0.7))
                local freeBattleDivReg = Region(matchDiv:getX(), matchDiv:getY(), matchDiv:getW(), matchDiv:getH())
                if freeBattleDivReg:exists("executedfreeBattle.png") then
                    print("免戰已存在")
                    file:write("免戰已存在\n")
                else
                    if freeBattleDivReg:exists("use.png") then
                        click(freeBattleDivReg:find("use.png"))
                        print("使用免戰")
                        file:write("使用免戰\n")
                        --aa = freeBattleDivReg:find("use.png")
                        --aa:highlight(4)
                    elseif freeBattleDivReg:exists("buyUse.png") then
                        click(freeBattleDivReg:find("buyUse.png"))
                        if existsClick("sure.png") then
                            print("購買免戰並使用")
                            file:write("購買免戰並使用\n")
                        end
                    end
                end
            end
        elseif (dialogReg:exists("beAttackedSoon.png")) then
            print("資源點被攻擊!!")
            file:write("資源點被攻擊!!\n")
            local beAttSoon = dialogReg:exists("beAttackedSoon.png") --366,91,66,11
            beAttLocation = Region(beAttSoon:getX() - 50, beAttSoon:getY(), beAttSoon:getW() - 20, beAttSoon:getH())
            --beAttLocation:highlight(3)
            click(beAttLocation)
            --print(find("resourceFlag.png"))--
            local centerReg = Region(318, 162, 80, 60)
            centerReg:highlight(2)
            local myResource = centerReg:exists("myResource.png")
            if myResource then
                click(myResource)
            end
            if (existsClick("comeBack.png")) then
                --資源點被打
                if (existsClick("retreat.png")) then
                    if (existsClick("sure.png")) then
                        print("召回成功")
                        file:write("召回成功!\n")
                    end
                end
            end
        else
            --跨服被打
            print("跨服被攻擊/已被攻擊完!!")
            toast("跨服被攻擊/已被攻擊完!!")
            file:write("跨服被攻擊/已被攻擊完!!\n")
            existsClick("close.png")
        end
    end
end

--檢查可招募及治療
function checkCabBeRecruited(isCross)
    if (existsClick("soldier.png")) then
        --print(find("recruite.png"))--495,308,880,548
        --local a = find("soldierPic.png")
        --print(soldierPicReg:find("canBeRecruited.png"))--161,112,286,199
        --local canBeRe = soldierPicReg:exists("canBeRecruited.png")
        --print(canBeRe)
        if (existsClick("canBeRecruited.png") and existsClick("recruite.png")) then
            if isCross then
                existsClick("recruite.png")
            end
            toast("自動招募")
            print("自動招募")
        end
        if (existsClick("treatable.png")) then
            toast("自動治療")
            print("自動治療")
            click("treatment.png")
        end
        existsClick("close.png")
    end
end

--檢查網路斷線
function checkNetwork()
    if (existsClick("NetworkDisconnection.png")) then
        print("網路斷線")
        toast("網路斷線")
        file:write("網路斷線\n")
        if existsClick("sure.png") then
            print("網路已重新連線")
            toast("網路已重新連線")
            file:write("網路已重新連線\n")
            wait(2)
        end
    end
end

--跨服自動招募及治療
function recruitInCross()
    toast("跨服自動招募及治療")
    local inHistoryBattle = exists("YuanShaoBack.png")
    if inHistoryBattle==nil then
        existsClick("historyBattle.png")
        wait(5)
    end
    checkCabBeRecruited(true)
    existsClick("historyBattleBack.png")
    wait(5)
end

--對話框
function dialog()
    --removePreference("cbValue")
    actions = { "採集", "打怪", "不動作" }
    resourceActions = { "農田", "伐木", "採石", "治煉" }
    directions = {"東","西","北","南","東北","西南","西北","東南"}

    dialogInit()
    --設定總隊數
    addTextView("目前的總隊數ex:4")
    addEditText("keyingTeams", "4")
    --addCheckBox("onlyHorseThief", "只打馬賊", false)
    addEditText("keyinSolderNum", "50000")
    addTextView("兵力少於多少不打怪")
    --跨服自動招募及治療
    addCheckBox("autoRecruitInCross", "跨服自動招募及治療", false)
    newRow()
    addCheckBox("autoResourceLevel", "設定採集等級", false)
    addEditText("resourceLevel", "6")
    addCheckBox("CheckFarmland", "農田", false)
    addCheckBox("CheckWood", "伐木", true)
    addCheckBox("CheckStone", "採石", true)
    addCheckBox("CheckIron", "治煉", true)
    newRow()
    --addTextView("(多個等級可用逗號分隔(ex:5,6,7),清空則不設定等級)")
    newRow()
    addTextView("隊伍1 動作:")
    addSpinnerIndex("actions1", actions, actions[2])
    addTextView("採集:")
    addSpinnerIndex("resourceActions1", resourceActions, resourceActions[1])
    addTextView("起始方向:")
    addSpinnerIndex("directions1", directions, directions[1])
    --addTextView("打怪/採集等級:")
    --addEditText("levels1", "")
    newRow()
    addTextView("隊伍2 動作:")
    addSpinnerIndex("actions2", actions, actions[1])
    addTextView("採集:")
    addSpinnerIndex("resourceActions2", resourceActions, resourceActions[1])
    addTextView("起始方向:")
    addSpinnerIndex("directions2", directions, directions[1])
    --addTextView("打怪/採集等級:")
    --addEditText("levels2", "4,5")
    newRow()
    addTextView("隊伍3 動作:")
    addSpinnerIndex("actions3", actions, actions[1])
    addTextView("採集:")
    addSpinnerIndex("resourceActions3", resourceActions, resourceActions[1])
    addTextView("起始方向:")
    addSpinnerIndex("directions3", directions, directions[1])
    --addTextView("打怪/採集等級:")
    --addEditText("levels3", "4,5")
    newRow()
    addTextView("隊伍4 動作:")
    addSpinnerIndex("actions4", actions, actions[1])
    addTextView("採集:")
    addSpinnerIndex("resourceActions4", resourceActions, resourceActions[1])
    addTextView("起始方向:")
    addSpinnerIndex("directions4", directions, directions[1])
    --addTextView("打怪/採集等級:")
    --addEditText("levels4", "4,5")
    newRow()
    addCheckBox("autoUseFreeBattle", "自動免戰及召回", true)
    --自動招募及治療
    addCheckBox("autoRecruit", "自動招募及治療", true)
    --自動使用行軍令
    addCheckBox("autoUse", "自動使用行軍令", true)
    --dialogShow("請輸入以下資訊後點選OK。ver:".. thisVersion)
    --等待秒數
    --addTextView("等待秒數")
    --addEditText("inputWaitSec", "10")
    dialogShow("請輸入以下資訊後點選OK。ver:".. thisVersion)
end

--------------------------- START
-- 起始時間,加零轉INT
startTime = 0 + os.date("%Y%m%d%H%M%S")
print("startTime:" .. startTime)
file:write("startTime:" .. startTime .. "\n")
--test
--print(find("solderNum.png")) --313,100,65,15
--local goToHorseThief = dialogReg:exists(Pattern("horseThief.png"):similar(0.9))
--print(goToHorseThief)
--print(find(Pattern("solderNum1.png"):similar(0.8)))
-- change to your resolution
executeTimer = Timer()
--對話框
dialog()
BanditLevel = ""
WaitSecond = preferenceGetNumber(inputWaitSec, 10)
--隊伍有幾隊
allTeams = preferenceGetNumber(keyingTeams, 4)
--兵力少於多少不打怪
keyinSolderNum = preferenceGetNumber(keyinSolderNum, 30000)

--every 5 min check Be Recruited
checkRecruitedSeconds = 60 * 5
--Team Action to Array
teamActions[1] = actions1
teamActions[2] = actions2
teamActions[3] = actions3
teamActions[4] = actions4
--Team Resource Action
resourceActions[1] = resourceActions1
resourceActions[2] = resourceActions2
resourceActions[3] = resourceActions3
resourceActions[4] = resourceActions4
--Team Directions
teamDirections[1] = directions1
teamDirections[2] = directions2
teamDirections[3] = directions3
teamDirections[4] = directions4
castleBeCenter()
--dialogReg:save(dirName .. "1.jpg")
--[[
west = find("flag.png")
building = find("building.png")
teamDirections[4] = directions4
print(west)
print(building)
Region(west:getX(),west:getY(),west:getH(),west:getW()):highlight(3)
Region(building:getX(),building:getY(),building:getH(),building:getW()):highlight(3)
--設定採集等級,空白則不設定
if resourceLevel ~= "" then
    settingResourceLevel(resourceLevel)
end
--設定打怪等級,空白則不設定
if BanditLevel ~= "" then
    settingBandit(BanditLevel)
end
--]]
--兵力太少休10分/找不到
local notFoundBanditTeam = 0
local excuteCount = 0

-- 方向
direction = 1
while (true) do
    if excuteCount > 5000 then
        print("執行" .. excuteCount .. "次結束!")
        return -1
    end
    if excuteCount % 20 == 0 then
        file:close()
        file = io.open(fileName, "a")
    end
    checkNetwork()
    if autoUseFreeBattle then
        toast("檢查兵臨城下")
        checkBeAttacked()
    end
    local executeTime = 0 + executeTimer:check()
    if autoRecruit and (excuteCount == 0 or executeTime > checkRecruitedSeconds) then
        -- 下次檢查時間
        if (excuteCount > 0) then
            checkRecruitedSeconds = checkRecruitedSeconds + checkRecruitedSeconds
        end
        if autoRecruitInCross then
            recruitInCross()
        end
        toast("檢查可招募")
        checkCabBeRecruited(false)
    end
    if excuteCount == 0 and autoResourceLevel then
        --設定採集等級
        settingResourceLevel(resourceLevel)
    end
    if actions1 == 3 and actions2 == 3 and actions3 == 3 and actions4 == 3 then
        toast("等待" .. WaitSecond .. "秒!")
        wait(WaitSecond)
    else
        toast("查找可用閒置隊伍")
        local unavailable = Pattern("allTeams" .. allTeams .. ".png"):similar(0.93)
        if exists(unavailable) then
            toast("無可用閒置隊伍,等待" .. WaitSecond .. "秒!")
            wait(WaitSecond)
        else
            if (existsClick("flag.png")) then
                local teamNum = 1
                while teamNum <= allTeams do
                    --toast("teamNum:" .. teamNum)
                    --toast("notFoundBanditTeam:" .. notFoundBanditTeam)
                    if teamNum ~= notFoundBanditTeam then
                        local teamStandy = Pattern("team" .. teamNum .. "Standy.png"):similar(0.95)

                        if teamActions[teamNum] ~= 3 then
                            local aviTeam = exists(teamStandy)
                            if aviTeam then
                                if keyinSolderNum and teamActions[teamNum] == 2 then
                                    click(aviTeam)
                                    local solderReg = Region(313, 100, 68, 20):highlight(3)
                                    local solderNum = numberOCR(solderReg, "wi")
                                    toast("keyinSolderNum:"..keyinSolderNum .." Solders:"..solderNum)
                                    if tonumber(solderNum) < keyinSolderNum then
                                        toast("兵力太少:" .. solderNum)
                                        existsClick("close.png")
                                        notFoundBanditTeam = teamNum
                                        break
                                    end
                                end
                                toast("team" .. teamNum .. " is standy")
                                --print(teamStandy)
                                existsClick("close.png")
                                local result = findBattle(teamNum)
                                if autoUseFreeBattle then
                                    toast("檢查兵臨城下")
                                    checkBeAttacked()
                                end
                                if result == 2 then
                                    toast("無可用閒置隊伍,等待" .. WaitSecond .. "秒!")
                                    wait(WaitSecond)
                                elseif result == 4 then
                                    toast("找不到" .. BanditLevel .. "寇匪/資源")
                                    direction = 1
                                    file:write("team"..teamNum.."找不到寇匪/資源!!\n")
                                    notFoundBanditTeam = teamNum
                                    break
                                elseif result == 1 then
                                    toast("移動中請等待" .. WaitSecond .. "秒!")
                                    wait(WaitSecond)
                                    notFoundBanditTeam = 0
                                end
                            end
                        end
                    end
                    teamNum = teamNum + 1
                end
                existsClick("close.png")
            end
        end
    end
    excuteCount = excuteCount + 1
end


