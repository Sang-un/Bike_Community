import * as Yup from 'yup';
import { useState, React } from 'react';
// form
import { useForm, Controller } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
// @mui
import { Stack, IconButton, InputAdornment, Alert, Typography, Accordion, AccordionSummary, AccordionDetails,Autocomplete,Chip } from '@mui/material';
import { LoadingButton } from '@mui/lab';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
// hooks
import useAuth from '../../../hooks/useAuth';
import useIsMountedRef from '../../../hooks/useIsMountedRef';
// components
import Iconify from '../../../components/Iconify';
import { FormProvider, RHFTextField, RHFCheckbox } from '../../../components/hook-form'
import Regicheck1 from './Rescheck1';
import Regicheck2 from './Rescheck2';
import Regicheck3 from './Rescheck3';


// ----------------------------------------------------------------------

export default function RegisterForm() {
  const { register } = useAuth();

  const isMountedRef = useIsMountedRef();

  const [showPassword, setShowPassword] = useState(false);

  const RegisterSchema = Yup.object().shape({
    email: Yup.string().email('유효한 이메일 주소를 입력해주세요.')
                       .required('이메일을 입력해주세요.'),
    password: Yup.string().required('비밀번호를 입력해주세요.')
                          .matches(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/,"최소 8자 이상으로 소문자, 대문자, 특수문자, 숫자를 최소 한개씩 포함해야 합니다.")
                          .min(8, '비밀번호는 최소 8자 이상입니다.'),
    password2: Yup.string().required('비밀번호를 입력해주세요.')
                           .oneOf(([Yup.ref('password'),null]),('비밀번호가 일치하지 않습니다.')),
    username: Yup.string().required('이름을 입력해주세요.'),
    nickname: Yup.string().required('닉네임을 입력해주세요.')
                          .max(8, '닉네임은 8자리 이하로 입력해주세요.')
                          .min(2, '닉네임은 2자리 이상으로 입력해주세요.'),
    birthday: Yup.string().required('생일을 입력해주세요.')
                       .max(6, '8자리를 입력해주세요.')
                       .min(6, '8자리를 입력해주세요.')
                       .matches(/^[0123456789]*$/, "숫자만 적어주세요. "),
    phone: Yup.string().required('핸드폰 번호를 입력해주세요.')
                       .matches(/^[0123456789]*$/, "숫자만 적어주세요. ")
                       .max(11, '11자리를 입력해주세요.')
                       .min(11, '11자리를 입력해주세요.'),
    sex: Yup.string().required('성별을 입력해주세요.'),
    city: Yup.string().required('지역명을 입력해주세요.'),
    resisteragree1: Yup.boolean().oneOf([true], '체크해주세요.').required('체크해주세요.'),
    resisteragree2: Yup.boolean().oneOf([true], '체크해주세요.').required('체크해주세요.'),
    resisteragree3: Yup.boolean().oneOf([true], '체크해주세요.').required('체크해주세요.'),
  });

  const SEX_OPTION = [
    '남', 
    '여',
  ];
  

  const defaultValues = {
    email: '',
    password: '',
    password2: '',
    username: '',
    nickname: '',
    birthday: '',
    phone: '',    
    sex: '',   
    city: '', 
    resisteragree1:false,
    resisteragree2:false,
    resisteragree3:false,
  };

  const methods = useForm({
    resolver: yupResolver(RegisterSchema),
    defaultValues,   
  });

  const {
    reset,
    setError,
    handleSubmit,
    control,
    formState: { errors, isSubmitting },
  } = methods;

  const onSubmit = async (data) => {
    try {
      await register(
        data.email, 
        data.password,
        data.username, 
        data.nickname,
        data.birthday,
        data.phone,
        data.sex,
        data.city,);
    } catch (error) {
      console.error(error);
      reset();
      if (isMountedRef.current) {
        setError('afterSubmit', error);
      }
    }
  };

  return (
    <FormProvider methods={methods} onSubmit={handleSubmit(onSubmit)}>
      <Stack spacing={3}>
        {!!errors.afterSubmit && <Alert severity="error">{errors.afterSubmit.message}</Alert>}        
        <RHFTextField name="email" label="이메일" />
        
        <RHFTextField
          name="password"
          label="비밀번호"
          type={showPassword ? 'text' : 'password'}
          helperText="최소 8자 이상으로, 대문자, 소문자, 특수문자가 한개 이상 들어가게 설정해주세요."
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton edge="end" onClick={() => setShowPassword(!showPassword)}>
                  <Iconify icon={showPassword ? 'eva:eye-fill' : 'eva:eye-off-fill'} />
                </IconButton>
              </InputAdornment>
            ),
          }}
        />
        <RHFTextField
          name="password2"
          label="비밀번호 확인"
          type={showPassword ? 'text' : 'password'}
          helperText="앞에 입력한 비밀번호와 같은 값을 입력해주세요."
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton edge="end" onClick={() => setShowPassword(!showPassword)}>
                  <Iconify icon={showPassword ? 'eva:eye-fill' : 'eva:eye-off-fill'} />
                </IconButton>
              </InputAdornment>
            ),
          }}
        />

        <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
          <RHFTextField name="username" label="이름" helperText="한글로 입력해주세요."/>
          <RHFTextField name="nickname" label="닉네임"  helperText="2자 이상 8자 이하로 입력해주세요."/>  
          </Stack>
        <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
        <RHFTextField name="birthday" label="생일"  helperText="'-'없이 6자리를 입력해주세요."  placeholder="YYMMDD"/>        
        <RHFTextField name="phone" label="핸드폰 번호" helperText="'-'없이 11자리를 입력해주세요."  placeholder="01012345678"/>
        </Stack>
        <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
        <RHFTextField name="city" label="지역" />                
        <Controller
                  name="sex"
                  control={control}
                  render={({ field }) => (
                    <Autocomplete
                      onChange={(event, newValue) => field.onChange(newValue)}
                      options={SEX_OPTION.map((option) => option)}
                      renderTags={(value, getTagProps) =>
                        value.map((option, index) => (
                          <Chip {...getTagProps({ index })} key={option} size="small" label={option} />
                        ))
                      }
                      renderInput={(params) => <RHFTextField name="sex" label="성별" {...params} />}
                    />
                  )}
                />   
        </Stack>

        <Stack spacing={3}>
        <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1a-content"
          id="panel1a-header">
          <Typography>라이더 타운 이용약관</Typography>
        </AccordionSummary>
        <AccordionDetails>        
        <Regicheck1/>
        </AccordionDetails>
      </Accordion>
      <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel2a-content"
          id="panel2a-header">
          <Typography>개인정보 처리 약관</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Regicheck2/>
        </AccordionDetails>
      </Accordion>      
      <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel2a-content"
          id="panel2a-header">
          <Typography>개인정보 처리 약관</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Regicheck3/>
        </AccordionDetails>
      </Accordion>
      </Stack>


        <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ my: 2 }}> 
        <Typography variant="body2" align="center" sx={{ color: 'text.secondary' }}>
         <strong>라이더타운 이용약관</strong>에 동의
        </Typography>   
        <RHFCheckbox name="resisteragree1" label="" /> 
        </Stack>
        {errors.resisteragree1 && <Alert severity="error"><strong>라이더타운 이용 약관에 동의해주세요.</strong></Alert>}
        
        <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ my: 2 }}> 
        <Typography variant="body2" align="center" sx={{ color: 'text.secondary' }}>
        <strong>개인정보 처리약관</strong>에 동의
        </Typography>   
        <RHFCheckbox name="resisteragree2" label="" /> 
        </Stack>
        {errors.resisteragree2 && <Alert severity="error"><strong>개인정보 처리 약관에 동의해주세요.</strong></Alert>}
        
        <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ my: 2 }}> 
        <Typography variant="body2" align="center" sx={{ color: 'text.secondary' }}>
         <strong>전자상거래 이용약관</strong>에 동의
        </Typography>   
        <RHFCheckbox name="resisteragree3" label="" /> 
        </Stack>
        {errors.resisteragree3 && <Alert severity="error"><strong>전자상거래 이용 약관에 동의해주세요.</strong></Alert>}
        
     

        
        <LoadingButton fullWidth size="large" type="submit" variant="contained" loading={isSubmitting} >
          가입하기
        </LoadingButton>
      </Stack>
    </FormProvider>
  );
}
