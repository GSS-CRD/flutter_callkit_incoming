module.exports = function(context) {
  const fs = require('fs');
  const path = require('path');
  const { ConfigParser } = require('cordova-common');
  const projectRoot = context.opts.projectRoot;
  const config = new ConfigParser(path.join(projectRoot, 'config.xml'));
  const sourceRoot = projectRoot + '/plugins/flutter-callkit-incoming/android/src/main/kotlin/com/hiennv/flutter_callkit_incoming';
  const needImportR = ['widgets/RippleRelativeLayout.kt', 'CallkitIncomingActivity.kt', 'CallkitNotificationManager.kt', 'Utils.kt'];

  needImportR.forEach(file => {
    const filePath = path.join(sourceRoot, file);
    const fileContent = fs.readFileSync(filePath, 'utf8');
    const packageName = config.android_packageName() || config.packageName();

    if (!fileContent.includes(`import ${packageName}.R`)) {
      const importStatement = `import ${packageName}.R;\n`;
      const updatedContent = fileContent.replace(/package.*\n/, `$&\n${importStatement}`)
                                        .replace('import com.hiennv.flutter_callkit_incoming.R', '');
      fs.writeFileSync(filePath, updatedContent, 'utf8');
    }
  });
}