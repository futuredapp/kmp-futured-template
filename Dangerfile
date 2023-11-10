danger.import_dangerfile(github: "futuredapp/danger")

def report_android_lint(file_name)
  if File.file?(file_name)
    android_lint.report_file = file_name
    android_lint.filtering = true
    android_lint.skip_gradle_task = true
    android_lint.lint(inline_mode: true)
  end
end

def report_checkstyle(file_name)
  if File.file?(file_name)
    checkstyle_format.report file_name
  end
end

def report_all_android_lint_results(file_name)
  # Get the absolute path of the current directory
  current_directory = Dir.pwd

  # Recursively search for files in the current directory hierarchy
  # and call the report function for files matching the given file name
  Dir.glob("**/#{file_name}", File::FNM_PATHNAME | File::FNM_DOTMATCH) do |file_path|
    report_android_lint(file_path)
  end
end

def report_all_ktlint_results(file_name)
  # Get the absolute path of the current directory
  current_directory = Dir.pwd

  # Recursively search for files in the current directory hierarchy
  # and call the report function for files matching the given file name
  Dir.glob("**/#{file_name}", File::FNM_PATHNAME | File::FNM_DOTMATCH) do |file_path|
    report_checkstyle(file_path)
  end
end

# Setup checkstyle
checkstyle_format.base_path = Dir.pwd

# Detekt report
report_checkstyle 'build/reports/detekt/merged.xml'

# Ktlint report
report_all_ktlint_results 'ktlintKotlinScriptCheck.xml'
report_all_ktlint_results 'ktlintCommonMainSourceSetCheck.xml'
report_all_ktlint_results 'ktlintAndroidMainSourceSetCheck.xml'
report_all_ktlint_results 'ktlintIosMainSourceSetCheck.xml'

# Android lint report
report_all_android_lint_results 'lint-results-debug.xml'
report_all_android_lint_results 'lint-results-enterprise.xml'
report_all_android_lint_results 'lint-results-release.xml'
