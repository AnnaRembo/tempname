$parameter = $args[0]

#echo 'Map of process ID to CPU usage:'
Get-WmiObject -Query "Select * from Win32_PerfFormattedData_PerfProc_Process" | Select-Object -Property IDProcess,Name,PercentProcessorTime | Where-Object {$_.IDProcess -eq $parameter} | ConvertTo-Csv -NoTypeInformation