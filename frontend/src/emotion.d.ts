import { Colors, Typography } from '@_common/theme/theme.type';

declare module '@emotion/react' {
  export interface Theme {
    colors: Colors;
    typography: Typography;
  }
}
