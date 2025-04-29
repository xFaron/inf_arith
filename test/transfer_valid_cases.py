import os

OLD_TESTS_PATH = "test/old_tests"
TESTS_PATH = "test"

# OLD_TESTS_PATH = path if (path := input(f"Enter Path to old_tests folder [Press Enter for : {OLD_TESTS_PATH}]:")) else OLD_TESTS_PATH
# TESTS_PATH = path if (path := input(f"Enter Path to tests folder [Press Enter for : {TESTS_PATH}]:")) else TESTS_PATH

subfolders = ['input', 'output']

if not all(os.path.exists(os.path.join(TESTS_PATH, f)) and os.path.exists(os.path.join(OLD_TESTS_PATH, f)) for f in subfolders):
  print("Invalid paths or missing 'input'/'output' subfolders.")
  exit(-1)


for subfolder in subfolders:
  test_subfolder = os.path.join(TESTS_PATH, subfolder)
  old_test_subfolder = os.path.join(OLD_TESTS_PATH, subfolder)

  for filename in os.listdir(test_subfolder):
    test_file_path = os.path.join(test_subfolder, filename)
    old_test_file_path = os.path.join(old_test_subfolder, filename)

    try:
      os.rename(test_file_path, old_test_file_path)
      print(f"Moved: {test_file_path} → {old_test_file_path}")
    except Exception as e:
      print(f"Failed to move {test_file_path}: {e}")	
