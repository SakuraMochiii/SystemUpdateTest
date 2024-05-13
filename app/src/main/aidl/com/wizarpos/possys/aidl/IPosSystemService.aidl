package com.wizarpos.possys.aidl;
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