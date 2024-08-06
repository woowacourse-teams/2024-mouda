import { PropsWithChildren } from 'react';
import PleaseHeader from './PleaseHeader/PleaseHeader';
import PleaseMain from './PleaseMain/PleaseMain';
import PleaseFixedButtonWrapper from './PleaseFixedButtonWrapper/PleaseFixedButtonWrapper';
import * as S from './PleaseLayout.style';

function PleaseLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.containerStyle}>{children}</div>;
}

PleaseLayout.Header = PleaseHeader;
PleaseLayout.Main = PleaseMain;
PleaseLayout.HomeFixedButtonWrapper = PleaseFixedButtonWrapper;

export default PleaseLayout;
