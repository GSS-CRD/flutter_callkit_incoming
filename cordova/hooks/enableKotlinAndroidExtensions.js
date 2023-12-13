module.exports = function(context) {
  const fs = require('fs');
  const path = require('path');
  const projectRoot = context.opts.projectRoot;
  const gradleFile = projectRoot + '/platforms/android/app/build.gradle';

  // Read the content of the gradle file
  let content = fs.readFileSync(gradleFile, 'utf8');

  // Check if the 'kotlin-android-extensions' plugin is commented
  if (content.includes("// apply plugin: 'kotlin-android-extensions'")) {
    // Uncomment the 'kotlin-android-extensions' plugin
    content = content.replace("// apply plugin: 'kotlin-android-extensions'", "apply plugin: 'kotlin-android-extensions'");
    
    // Write the modified content back to the gradle file
    fs.writeFileSync(gradleFile, content, 'utf8');
  }
}