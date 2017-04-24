local arr = {0, 1}
local arr1 = {2, 3}
for key, value in ipairs(arr1) do      
    table.insert(arr, value)  
end  
return arr
