/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	//config.height = '600px';
	//config.width = '900px';
	config.filebrowserBrowseUrl = '/libraries/ckfinder/ckfinder.html';
 
	config.filebrowserImageBrowseUrl = '/libraries/ckfinder/ckfinder.html?type=Images';
	 
	config.filebrowserFlashBrowseUrl = '/libraries/ckfinder/ckfinder.html?type=Flash';
	 
	config.filebrowserUploadUrl = '/libraries/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files';
	 
	config.filebrowserImageUploadUrl = '/libraries/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Images';
	 
	config.filebrowserFlashUploadUrl = '/libraries/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Flash';

};
