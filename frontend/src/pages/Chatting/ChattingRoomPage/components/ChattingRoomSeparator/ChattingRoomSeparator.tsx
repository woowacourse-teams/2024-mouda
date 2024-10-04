import * as S from './ChattingRoomSeparator.style';

import { useTheme } from '@emotion/react';

interface ChattingRoomSeparatorProps {
  string: string;
}

export default function ChattingRoomSeparator(
  props: ChattingRoomSeparatorProps,
) {
  const { string } = props;
  const theme = useTheme();

  return (
    <div css={S.separatorWrapper}>
      <span
        css={[
          S.separator({ theme }),
          theme.coloredTypography.small(theme.colorPalette.black[70]),
        ]}
      >
        {string}
      </span>
    </div>
  );
}
