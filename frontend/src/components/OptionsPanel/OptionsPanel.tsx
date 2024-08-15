interface OptionsPanelProps {
  options: {
    description: string;
    onClick: () => void;
    hasTopBorder?: boolean;
  }[];
  onClose: () => void;
  movedWidth?: string;
  width?: string;
}

import * as S from './OptionsPanel.style';

import { useEffect } from 'react';
import { useTheme } from '@emotion/react';

export default function OptionsPanel(props: OptionsPanelProps) {
  const { options, onClose, width, movedWidth } = props;
  const theme = useTheme();

  useEffect(() => {
    document.addEventListener('click', onClose);
    return () => document.removeEventListener('click', onClose);
  }, [onClose]);

  console.log(options);
  return (
    <div css={S.panel(width || '100px', movedWidth || '0px')}>
      {options.map(({ description, onClick, hasTopBorder }) => {
        return (
          <div
            key={description}
            onClick={onClick}
            css={S.option({ theme, hasTopBorder })}
          >
            <span css={theme.typography.b1}>{description}</span>
          </div>
        );
      })}
    </div>
  );
}
