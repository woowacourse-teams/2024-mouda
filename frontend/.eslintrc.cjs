module.exports = {
  root: true,
  env: { browser: true, es2020: true },
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:react-hooks/recommended',
    'plugin:react/recommended',
    'plugin:storybook/recommended',
    'prettier',
  ],
  ignorePatterns: [
    'dist',
    '.eslintrc.cjs',
    'node_modules/',
    'webpack.common.js',
    'webpack.dev.js',
    'webpack.prod.js',
  ],
  parser: '@typescript-eslint/parser',
  plugins: ['react-refresh', '@emotion', "compat"],
  settings: {
    react: {
      version: 'detect',
    },
  },
  rules: {
    "compat/compat": "warn",
    'react/react-in-jsx-scope': 'off',
    'react-refresh/only-export-components': [
      'warn',
      { allowConstantExport: true },
    ],
    'react/no-unknown-property': ['error', { ignore: ['css'] }],
  },
};
