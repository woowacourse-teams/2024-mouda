import * as S from './ChatMenuItem.style';

import { ReactNode } from 'react';
import { useTheme } from '@emotion/react';

interface ChatMenuItemProps {
  icon: ReactNode;
  description: string;
  onClick?: () => void;
}

export default function ChatMenuItem(props: ChatMenuItemProps) {
  const { icon, description, onClick } = props;
  const theme = useTheme();

  return (
    <div css={S.item} onClick={onClick}>
      <div css={S.button({ theme })}>{icon}</div>
      <div
        css={[
          S.textBox,
          theme.coloredTypography.label(theme.colorPalette.grey[500]),
        ]}
      >
        {description}
      </div>
    </div>
  );
}
