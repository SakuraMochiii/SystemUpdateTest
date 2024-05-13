1：The call permission required by the interface
"android.permission.CLOUDPOS_SYS_UPDATE"
2：interface specification
package com.cloudpos.possys.aidl;
interface IPosSystemService{
	/**
	*	@filePath	:firmware file path.
	*	@return 	0 : success pos
	*				-1：unknown error.
	*				-2：file io exception.
	*				-3：firmware file error.
	*				-4：firmware file not exist.
	**/
   int updateSystem(String path);
}
