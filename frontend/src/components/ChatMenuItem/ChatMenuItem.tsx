import * as S from './ChatMenuItem.style';

import { PropsWithChildren, ReactNode } from 'react';

import { useTheme } from '@emotion/react';

interface ChatMenuItemProps extends PropsWithChildren {
  icon: ReactNode;
  description: string;
}

export default function ChatMenuItem(props: ChatMenuItemProps) {
  const { icon, description } = props;
  const theme = useTheme();

  return (
    <div css={S.item}>
      <div css={S.button({ theme })}>{icon}</div>
      <span css={theme.coloredTypography.label(theme.colorPalette.grey[500])}>
        {description}
      </span>
    </div>
  );
}
