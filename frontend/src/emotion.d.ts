import { Colors, Typography } from '@_common/theme/theme.type';

declare module '@emotion/react' {
  export interface Theme {
    colorPalette: Colors;
    typography: Typography;
    semantic: Semantic;
    layout: Layout;
  }
}
