module.exports = function(context) {
  const fs = require('fs');
  const path = require('path');
  const { ConfigParser } = require('cordova-common');
  const projectRoot = context.opts.projectRoot;
  const config = new ConfigParser(path.join(projectRoot, 'config.xml'));
  const sourceRoot = projectRoot + '/plugins/flutter-callkit-incoming/ios/Classes';
  const needRenameData = ['Call.swift', 'CallManager.swift'];

  needRenameData.forEach(fileName => {
    const filePath = path.join(sourceRoot, fileName);
    let fileContent = fs.readFileSync(filePath, 'utf8');
    fileContent = fileContent.replace(/class Data: NSObject/g, 'class CallInComingData: NSObject');
    fileContent = fileContent.replace(/data: Data/g, 'data: CallInComingData');
    fs.writeFileSync(filePath, fileContent, 'utf8');
  });
}