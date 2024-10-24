import { PropsWithChildren } from 'react';
import PleaseHeader from './PleaseHeader/PleaseHeader';
import PleaseMain from './PleaseMain/PleaseMain';
import PleaseFixedButtonWrapper from './PleaseFixedButtonWrapper/PleaseFixedButtonWrapper';
import * as S from './PleaseLayout.style';
import { useTheme } from '@emotion/react';

function PleaseLayout(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <div css={S.containerStyle({ theme })}>{children}</div>;
}

PleaseLayout.Header = PleaseHeader;
PleaseLayout.Main = PleaseMain;
PleaseLayout.HomeFixedButtonWrapper = PleaseFixedButtonWrapper;

export default PleaseLayout;
