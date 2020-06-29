$parameter = $args[0]

Get-WmiObject -Query "Select * from Win32_PerfFormattedData_PerfProc_Process" | Select-Object -Property IDProcess,Name,WorkingSet  | Where-Object {$_.IDProcess -eq $parameter} | ConvertTo-Csv -NoTypeInformation